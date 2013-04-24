/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.nio.intraband.messaging;

import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.MockIntraband;
import com.liferay.portal.kernel.nio.intraband.MockRegistrationReference;
import com.liferay.portal.kernel.nio.intraband.PortalExecutorManagerUtilAdvice;
import com.liferay.portal.kernel.nio.intraband.SystemDataType;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJMockingNewClassLoaderJUnitTestRunner;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(AspectJMockingNewClassLoaderJUnitTestRunner.class)
public class MessageDatagramReceiveHandlerTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@AdviseWith(
		adviceClasses = {
			MessageBusUtilAdvice.class, PortalExecutorManagerUtilAdvice.class
		}
	)
	@Test
	public void testDoReceive() throws Exception {
		PortalClassLoaderUtil.setClassLoader(
			MessageDatagramReceiveHandlerTest.class.getClassLoader());

		MessageDatagramReceiveHandler messageDatagramReceiveHandler =
			new MessageDatagramReceiveHandler();

		SystemDataType systemDataType = SystemDataType.MESSAGE;

		Serializer serializer = new Serializer();

		Message message = new Message();

		String destinationName =
			MessageDatagramReceiveHandlerTest.class.getName();

		message.setDestinationName(destinationName);

		String payload = "payload";

		message.setPayload(payload);

		serializer.writeObject(message);

		Datagram datagram = Datagram.createRequestDatagram(
			systemDataType.getValue(), serializer.toByteBuffer());

		messageDatagramReceiveHandler.doReceive(
			_mockRegistrationReference, datagram);

		Assert.assertEquals(
			destinationName, MessageBusUtilAdvice._destinationNamse);
		Assert.assertNotNull(MessageBusUtilAdvice._message);

		Message recordedMessage = MessageBusUtilAdvice._message;

		Assert.assertEquals(payload, recordedMessage.getPayload());
	}

	@Aspect
	public static class MessageBusUtilAdvice {

		@Around(
			"execution(public static void com.liferay.portal.kernel." +
				"messaging.MessageBusUtil.sendMessage(String, " +
					"com.liferay.portal.kernel.messaging.Message)) && args(" +
						"destinationName, message)")
		public void sendMessage(String destinationName, Message message) {
			_destinationNamse = destinationName;
			_message = message;
		}

		private static String _destinationNamse;
		private static Message _message;

	}

	private MockIntraband _mockIntraband = new MockIntraband();
	private MockRegistrationReference _mockRegistrationReference =
		new MockRegistrationReference(_mockIntraband);

}