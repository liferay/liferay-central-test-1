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

package com.liferay.portlet.blogs.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portlet.blogs.util.LinkbackConsumerUtil;
import com.liferay.portlet.blogs.util.LinkbackProducerUtil;

/**
 * <a href="LinkbackMessageListener.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Alexander Chow
 * @author Tina Tian
 */
public class LinkbackMessageListener implements MessageListener {

	public void receive(Message message) {
		try {
			doReceive(message);
		}
		catch (Exception e) {
			_log.error("Unable to process message " + message, e);
		}
	}

	protected void doReceive(Message message) throws Exception {
		LinkbackConsumerUtil.verifyNewTrackbacks();
		LinkbackProducerUtil.sendQueuedPingbacks();
	}

	private static Log _log = LogFactoryUtil.getLog(
		LinkbackMessageListener.class);

}