/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.increment;
import com.liferay.portal.kernel.concurrent.BatchablePipe;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.util.PropsValues;

/**
 * @author Shuyang Zhou
 */
public class BufferedIncrementMessageListener implements MessageListener {

	public void receive(Message message) {
		try {
			doReceive(message);
		}
		catch (Exception e) {
			_log.error("Unable to process message " + message, e);
		}

	}

	protected void doReceive(Message message) throws Exception {
		BatchablePipe<String, BufferedIncreasableEntry> pipe =
			(BatchablePipe<String, BufferedIncreasableEntry>)
				message.getPayload();

		BufferedIncreasableEntry entry = null;
		while ((entry = (BufferedIncreasableEntry) pipe.take()) != null) {
			try {
				entry.proceed();
			}
			catch (Throwable t) {
				_log.error("Cannot write buffered increment value to the "
					+ "database", t);
			}
			if (PropsValues.SOCIAL_EQUITY_EQUITY_VALUE_BUFFERED_INCREMENT_LAZILY) {
				return;
			}
		}

	}

	private static Log _log = LogFactoryUtil.getLog(
		BufferedIncrementMessageListener.class);

}