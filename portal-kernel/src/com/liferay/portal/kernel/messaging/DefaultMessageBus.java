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

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="DefaultMessageBus.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 *
 */
public class DefaultMessageBus implements MessageBus {

	public synchronized void addDestination(Destination destination) {
		_destinations.put(destination.getName(), destination);

		Destination responseDestination = getResponseDestination(destination);

		_destinations.put(responseDestination.getName(), responseDestination);

		if (destination.getName().equals(DestinationNames.GLOBAL)) {
			_globalDestinationsCount++;
		}
	}

	public boolean hasMessageListener(String destination) {
		Destination destinationModel = _destinations.get(destination);

		if ((destinationModel != null) && destinationModel.isRegistered()) {
			return true;
		}
		else {
			return false;
		}
	}

	public synchronized void registerMessageListener(
		String destination, MessageListener listener) {

		Destination destinationModel = _destinations.get(destination);

		if (destinationModel == null) {
			throw new IllegalStateException(
				"Destination " + destination + " is not configured");
		}

		destinationModel.register(listener);
	}

	public synchronized void removeDestination(String destination) {
		_destinations.remove(destination);

		String responseDestination = getResponseDestination(destination);

		_destinations.remove(responseDestination);

		if (destination.equals(DestinationNames.GLOBAL)) {
			_globalDestinationsCount--;
		}
	}

	public void sendMessage(String destination, Object message) {
		if ((_globalDestinationsCount > 0) &&
			(!destination.equals(DestinationNames.GLOBAL))) {

			sendMessage(DestinationNames.GLOBAL, message);
		}

		Destination destinationModel = _destinations.get(destination);

		if (destinationModel == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Destination " + destination + " is not configured");
			}

			return;
		}

		destinationModel.send(message);
	}

	public void sendMessage(String destination, String message) {
		if ((_globalDestinationsCount > 0) &&
			(!destination.equals(DestinationNames.GLOBAL))) {

			sendMessage(DestinationNames.GLOBAL, message);
		}

		Destination destinationModel = _destinations.get(destination);

		if (destinationModel == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Destination " + destination + " is not configured");
			}

			return;
		}

		destinationModel.send(message);
	}

	public Object sendSynchronizedMessage(
			String destination, Message message, long timeout)
		throws MessageBusException {

		if ((_globalDestinationsCount > 0) &&
			(!destination.equals(DestinationNames.GLOBAL))) {

			sendMessage(DestinationNames.GLOBAL, message);
		}

		Destination destinationModel = _destinations.get(destination);

		if (destinationModel == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Destination " + destination + " is not configured");
			}

			return null;
		}

		Destination responseDestinationModel = _destinations.get(
			getResponseDestination(destination));

		if (responseDestinationModel == null) {
			_log.error(
				"Response destination " + destination + " is not configured");

			return null;
		}

		ObjectResponseMessageListener responseMessageListener =
			new ObjectResponseMessageListener(
				destinationModel, responseDestinationModel, getNextResponseId(),
				timeout);

		responseDestinationModel.register(responseMessageListener);

		try {
			Object responseValue = responseMessageListener.send(message);

			if ((_globalDestinationsCount > 0) &&
				(!destination.equals(DestinationNames.GLOBAL))) {

				sendMessage(DestinationNames.GLOBAL, responseValue);
			}

			return responseValue;
		}
		finally {
			responseDestinationModel.unregister(responseMessageListener);
		}
	}

	public String sendSynchronizedMessage(
			String destination, String message, long timeout)
		throws MessageBusException {

		if ((_globalDestinationsCount > 0) &&
			(!destination.equals(DestinationNames.GLOBAL))) {

			sendMessage(DestinationNames.GLOBAL, message);
		}

		Destination destinationModel = _destinations.get(destination);

		if (destinationModel == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Destination " + destination + " is not configured");
			}

			return null;
		}

		Destination responseDestinationModel = _destinations.get(
			getResponseDestination(destination));

		if (responseDestinationModel == null) {
			_log.error(
				"Response destination " + destination + " is not configured");

			return null;
		}

		StringResponseMessageListener responseMessageListener =
			new StringResponseMessageListener(
				destinationModel, responseDestinationModel, getNextResponseId(),
				timeout);

		responseDestinationModel.register(responseMessageListener);

		try {
			String responseValue = responseMessageListener.send(message);

			if ((_globalDestinationsCount > 0) &&
				(!destination.equals(DestinationNames.GLOBAL))) {

				sendMessage(DestinationNames.GLOBAL, responseValue);
			}

			return responseValue;
		}
		finally {
			responseDestinationModel.unregister(responseMessageListener);
		}
	}

	public void shutdown() {
		shutdown(false);
	}

	public synchronized void shutdown(boolean force) {
		for (Destination destination : _destinations.values()) {
			destination.close(force);
		}
	}

	public synchronized boolean unregisterMessageListener(
		String destination, MessageListener listener) {

		Destination destinationModel = _destinations.get(destination);

		if (destinationModel == null) {
			return false;
		}

		return destinationModel.unregister(listener);
	}

	protected String getNextResponseId() {
		return PortalUUIDUtil.generate();
	}

	protected String getResponseDestination(String destination) {
		return destination + _RESPONSE_DESTINATION_SUFFIX;
	}

	protected Destination getResponseDestination(Destination destination) {
		return new TempDestination(
			getResponseDestination(destination.getName()));
	}

	private static final String _RESPONSE_DESTINATION_SUFFIX = "/response";

	private static Log _log = LogFactoryUtil.getLog(DefaultMessageBus.class);

	private Map<String, Destination> _destinations =
		new HashMap<String, Destination>();
	private int _globalDestinationsCount;

}