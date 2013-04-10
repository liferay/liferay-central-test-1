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

package com.liferay.portal.kernel.nio.intraband.nonblocking;

import com.liferay.portal.kernel.nio.intraband.BaseIntraBand;
import com.liferay.portal.kernel.nio.intraband.BaseIntraBandHelper;
import com.liferay.portal.kernel.nio.intraband.ClosedIntraBandException;
import com.liferay.portal.kernel.nio.intraband.CompletionHandler.CompletionType;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.DatagramHelper;
import com.liferay.portal.kernel.nio.intraband.IntraBandTestUtil;
import com.liferay.portal.kernel.nio.intraband.RecordCompletionHandler;
import com.liferay.portal.kernel.nio.intraband.RecordDatagramReceiveHandler;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJMockingNewClassLoaderJUnitTestRunner;

import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;
import java.nio.channels.Pipe;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.Charset;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.aspectj.lang.annotation.Aspect;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(AspectJMockingNewClassLoaderJUnitTestRunner.class)
public class SelectorIntraBandTest {

	@Before
	public void setUp() throws Exception {
		_selectorIntraBand = new SelectorIntraBand(_DEFAULT_TIMEOUT);
	}

	@After
	public void tearDown() throws Exception {
		_selectorIntraBand.close();
	}

	@Test
	public void testCreateAndDestroy() throws Exception {

		// Close selector, with log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			SelectorIntraBand.class.getName(), Level.INFO);

		Thread wakeUpThread = new Thread(
			new WakeUpRunnable(_selectorIntraBand));

		wakeUpThread.start();

		Thread pollingThread = _selectorIntraBand.pollingThread;

		Selector selector = _selectorIntraBand.selector;

		synchronized (selector) {
			wakeUpThread.interrupt();

			wakeUpThread.join();

			while (pollingThread.getState() != Thread.State.BLOCKED);

			selector.close();
		}

		pollingThread.join();

		Assert.assertEquals(1, logRecords.size());

		String pollingThreadName = pollingThread.getName();

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(
			pollingThreadName.concat(" exiting gracefully on selector closure"),
			logRecord.getMessage());

		// Close selector, without log

		_selectorIntraBand = new SelectorIntraBand(1000);

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			SelectorIntraBand.class.getName(), Level.OFF);

		wakeUpThread = new Thread(new WakeUpRunnable(_selectorIntraBand));

		wakeUpThread.start();

		pollingThread = _selectorIntraBand.pollingThread;

		selector = _selectorIntraBand.selector;

		synchronized (selector) {
			wakeUpThread.interrupt();

			wakeUpThread.join();

			while (pollingThread.getState() != Thread.State.BLOCKED);

			selector.close();
		}

		pollingThread.join();

		Assert.assertTrue(logRecords.isEmpty());
	}

	@AdviseWith(adviceClasses = {Jdk14LogImplAdvice.class})
	@Test
	public void testReceiveDatagram() throws Exception {

		// Receive ACK response, no ACK request, with log

		Pipe readPipe = Pipe.open();
		Pipe writePipe = Pipe.open();

		GatheringByteChannel gatheringByteChannel = writePipe.sink();
		ScatteringByteChannel scatteringByteChannel = readPipe.source();

		SelectionKeyRegistrationReference registrationReference =
			(SelectionKeyRegistrationReference)
				_selectorIntraBand.registerChannel(
					writePipe.source(), readPipe.sink());

		long sequenceId = 100;

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.WARNING);

		Jdk14LogImplAdvice.reset();

		try {
			DatagramHelper.writeTo(
				DatagramHelper.createACKResponseDatagram(sequenceId),
				gatheringByteChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilWarnCalled();
		}

		Assert.assertEquals(1, logRecords.size());

		assertMessageStartWith(
			logRecords.get(0), "Dropped ownerless ACK response ");

		// Receive ACK response, no ACK request, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		Jdk14LogImplAdvice.reset();

		try {
			DatagramHelper.writeTo(
				DatagramHelper.createACKResponseDatagram(sequenceId),
				gatheringByteChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilIsWarnEnableCalled();
		}

		Assert.assertTrue(logRecords.isEmpty());

		// Receive ACK response, with ACK request

		Datagram requestDatagram = Datagram.createRequestDatagram(_type, _data);

		DatagramHelper.setAttachment(requestDatagram, new Object());

		RecordCompletionHandler<Object> recordCompletionHandler =
			new RecordCompletionHandler<Object>();

		DatagramHelper.setCompletionHandler(
			requestDatagram, recordCompletionHandler);

		DatagramHelper.setSequenceId(requestDatagram, sequenceId);
		DatagramHelper.setTimeout(requestDatagram, 10000);

		BaseIntraBandHelper.addResponseWaitingDatagram(
			_selectorIntraBand, requestDatagram);

		DatagramHelper.writeTo(
			DatagramHelper.createACKResponseDatagram(sequenceId),
			gatheringByteChannel);

		recordCompletionHandler.waitUntilDelivered();

		Assert.assertSame(
			DatagramHelper.getAttachment(requestDatagram),
			recordCompletionHandler.getAttachment());

		// Receive response, no request, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.WARNING);

		Jdk14LogImplAdvice.reset();

		try {
			DatagramHelper.writeTo(
				Datagram.createResponseDatagram(requestDatagram, _data),
				gatheringByteChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilWarnCalled();
		}

		Assert.assertEquals(1, logRecords.size());

		assertMessageStartWith(
			logRecords.get(0), "Dropped ownerless response ");

		// Receive response, no request, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		Jdk14LogImplAdvice.reset();

		try {
			requestDatagram = Datagram.createRequestDatagram(_type, _data);

			DatagramHelper.setSequenceId(requestDatagram, sequenceId);

			DatagramHelper.writeTo(
				Datagram.createResponseDatagram(requestDatagram, _data),
				gatheringByteChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilIsWarnEnableCalled();
		}

		Assert.assertTrue(logRecords.isEmpty());

		// Receive response, with request, with replied completion handler

		requestDatagram = Datagram.createRequestDatagram(_type, _data);

		DatagramHelper.setAttachment(requestDatagram, new Object());

		recordCompletionHandler = new RecordCompletionHandler<Object>();

		DatagramHelper.setCompletionHandler(
			requestDatagram, recordCompletionHandler);

		DatagramHelper.setCompletionTypes(
			requestDatagram, EnumSet.of(CompletionType.REPLIED));
		DatagramHelper.setSequenceId(requestDatagram, sequenceId);
		DatagramHelper.setTimeout(requestDatagram, 10000);

		BaseIntraBandHelper.addResponseWaitingDatagram(
			_selectorIntraBand, requestDatagram);

		DatagramHelper.writeTo(
			Datagram.createResponseDatagram(requestDatagram, _data),
			gatheringByteChannel);

		recordCompletionHandler.waitUntilReplied();

		Assert.assertSame(
			DatagramHelper.getAttachment(requestDatagram),
			recordCompletionHandler.getAttachment());

		// Receive response, with request, without replied completion handler,
		// with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.WARNING);

		requestDatagram = Datagram.createRequestDatagram(_type, _data);

		DatagramHelper.setCompletionTypes(
			requestDatagram, EnumSet.noneOf(CompletionType.class));

		recordCompletionHandler = new RecordCompletionHandler<Object>();

		DatagramHelper.setCompletionHandler(
			requestDatagram, recordCompletionHandler);

		DatagramHelper.setSequenceId(requestDatagram, sequenceId);
		DatagramHelper.setTimeout(requestDatagram, 10000);

		BaseIntraBandHelper.addResponseWaitingDatagram(
			_selectorIntraBand, requestDatagram);

		Jdk14LogImplAdvice.reset();

		try {
			DatagramHelper.writeTo(
				Datagram.createResponseDatagram(requestDatagram, _data),
				gatheringByteChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilWarnCalled();
		}

		Assert.assertEquals(1, logRecords.size());

		assertMessageStartWith(
			logRecords.get(0), "Dropped unconcerned response ");

		// Receive response, with request, without replied completion handler,
		// without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		requestDatagram = Datagram.createRequestDatagram(_type, _data);

		DatagramHelper.setCompletionTypes(
			requestDatagram, EnumSet.noneOf(CompletionType.class));

		recordCompletionHandler = new RecordCompletionHandler<Object>();

		DatagramHelper.setCompletionHandler(
			requestDatagram, recordCompletionHandler);

		DatagramHelper.setSequenceId(requestDatagram, sequenceId);
		DatagramHelper.setTimeout(requestDatagram, 10000);

		BaseIntraBandHelper.addResponseWaitingDatagram(
			_selectorIntraBand, requestDatagram);

		Jdk14LogImplAdvice.reset();

		try {
			DatagramHelper.writeTo(
				Datagram.createResponseDatagram(requestDatagram, _data),
				gatheringByteChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilIsWarnEnableCalled();
		}

		Assert.assertTrue(logRecords.isEmpty());

		// Receive request, requires ACK, no datagram receive handler, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.WARNING);

		requestDatagram = Datagram.createRequestDatagram(_type, _data);

		DatagramHelper.setAckRequest(requestDatagram);
		DatagramHelper.setSequenceId(requestDatagram, sequenceId);

		Jdk14LogImplAdvice.reset();

		try {
			DatagramHelper.writeTo(requestDatagram, gatheringByteChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilWarnCalled();
		}

		Datagram ackResponseDatagram = readDatagramFully(scatteringByteChannel);

		Assert.assertEquals(
			sequenceId, DatagramHelper.getSequenceId(ackResponseDatagram));
		Assert.assertTrue(DatagramHelper.isAckResponse(ackResponseDatagram));

		ByteBuffer dataByteBuffer = ackResponseDatagram.getDataByteBuffer();

		Assert.assertEquals(0, dataByteBuffer.capacity());

		Assert.assertEquals(1, logRecords.size());

		assertMessageStartWith(logRecords.get(0), "Dropped ownerless request ");

		// Receive request, no datagram receive handler, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		requestDatagram = Datagram.createRequestDatagram(_type, _data);

		DatagramHelper.setSequenceId(requestDatagram, sequenceId);

		Jdk14LogImplAdvice.reset();

		try {
			DatagramHelper.writeTo(requestDatagram, gatheringByteChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilIsWarnEnableCalled();
		}

		Assert.assertTrue(logRecords.isEmpty());

		// Receive request, with datagram receive handler,

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.SEVERE);

		requestDatagram = Datagram.createRequestDatagram(_type, _data);

		DatagramHelper.setSequenceId(requestDatagram, sequenceId);

		RecordDatagramReceiveHandler recordDatagramReceiveHandler =
			new RecordDatagramReceiveHandler();

		_selectorIntraBand.registerDatagramReceiveHandler(
			_type, recordDatagramReceiveHandler);

		Jdk14LogImplAdvice.reset();

		try {
			DatagramHelper.writeTo(requestDatagram, gatheringByteChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilErrorCalled();
		}

		Datagram receiveDatagram =
			recordDatagramReceiveHandler.getReceiveDatagram();

		Assert.assertEquals(
			sequenceId, DatagramHelper.getSequenceId(receiveDatagram));
		Assert.assertEquals(_type, receiveDatagram.getType());

		dataByteBuffer = receiveDatagram.getDataByteBuffer();

		Assert.assertArrayEquals(_data, dataByteBuffer.array());
		Assert.assertEquals(1, logRecords.size());

		assertMessageStartWith(logRecords.get(0), "Unable to dispatch");

		unregisterChannels(registrationReference);

		gatheringByteChannel.close();
		scatteringByteChannel.close();
	}

	@Test
	public void testRegisterChannelDuplex() throws Exception {

		// Channel is null

		try {
			_selectorIntraBand.registerChannel(null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Channel is null", npe.getMessage());
		}

		// Channel is not of type ScatteringByteChannel

		try {
			_selectorIntraBand.registerChannel(
				IntraBandTestUtil.<Channel>createProxy(Channel.class));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Channel is not of type ScatteringByteChannel",
				iae.getMessage());
		}

		// Channel is not of type GatheringByteChannel

		try {
			_selectorIntraBand.registerChannel(
				IntraBandTestUtil.<Channel>createProxy(
					ScatteringByteChannel.class));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Channel is not of type GatheringByteChannel",
				iae.getMessage());
		}

		// Channel is not of type SelectableChannel

		try {
			_selectorIntraBand.registerChannel(
				IntraBandTestUtil.<Channel>createProxy(
					ScatteringByteChannel.class, GatheringByteChannel.class));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Channel is not of type SelectableChannel", iae.getMessage());
		}

		// Channel is not valid for reading

		try {
			_selectorIntraBand.registerChannel(
				new MockDuplexSelectableChannel(false, true));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Channel is not valid for reading", iae.getMessage());
		}

		// Channel is not valid for writing

		try {
			_selectorIntraBand.registerChannel(
				new MockDuplexSelectableChannel(true, false));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Channel is not valid for writing", iae.getMessage());
		}

		SocketChannel[] peerSocketChannels =
			IntraBandTestUtil.createSocketChannelPeers();

		try {
			SocketChannel socketChannel = peerSocketChannels[0];

			// Interruptted on register

			final Thread mainThread = Thread.currentThread();

			Thread wakeUpThread = new Thread(
				new WakeUpRunnable(_selectorIntraBand));

			Thread interruptThread = new Thread() {

				@Override
				public void run() {
					while (mainThread.getState() != Thread.State.WAITING);

					mainThread.interrupt();
				}

			};

			wakeUpThread.start();

			Selector selector = _selectorIntraBand.selector;

			synchronized (selector) {
				wakeUpThread.interrupt();
				wakeUpThread.join();

				interruptThread.start();

				try {
					_selectorIntraBand.registerChannel(socketChannel);

					Assert.fail();
				}
				catch (IOException ioe) {
					Throwable cause = ioe.getCause();

					Assert.assertTrue(cause instanceof InterruptedException);
				}

				interruptThread.join();
			}

			// Normal register

			SelectionKeyRegistrationReference
				selectionKeyRegistrationReference =
					(SelectionKeyRegistrationReference)
						_selectorIntraBand.registerChannel(socketChannel);

			Assert.assertNotNull(selectionKeyRegistrationReference);
			Assert.assertSame(
				selectionKeyRegistrationReference.readSelectionKey,
				selectionKeyRegistrationReference.writeSelectionKey);

			SelectionKey selectionKey =
				selectionKeyRegistrationReference.readSelectionKey;

			Assert.assertTrue(selectionKey.isValid());
			Assert.assertEquals(
				SelectionKey.OP_READ | SelectionKey.OP_WRITE,
				selectionKey.interestOps());
			Assert.assertNotNull(selectionKey.attachment());

			// Register after close

			_selectorIntraBand.close();

			try {
				_selectorIntraBand.registerChannel(socketChannel);

				Assert.fail();
			}
			catch (ClosedIntraBandException cibe) {
			}
		}
		finally {
			peerSocketChannels[0].close();
			peerSocketChannels[1].close();
		}
	}

	@Test
	public void testRegisterChannelReadWrite() throws Exception {

		// Scattering byte channel is null

		try {
			_selectorIntraBand.registerChannel(null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"Scattering byte channel is null", npe.getMessage());
		}

		// Gathering byte channel is null

		try {
			_selectorIntraBand.registerChannel(
				IntraBandTestUtil.<ScatteringByteChannel>createProxy(
					ScatteringByteChannel.class), null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"Gathering byte channel is null", npe.getMessage());
		}

		// Scattering byte channel is not of type SelectableChannel

		try {
			_selectorIntraBand.registerChannel(
				IntraBandTestUtil.<ScatteringByteChannel>createProxy(
					ScatteringByteChannel.class),
				IntraBandTestUtil.<GatheringByteChannel>createProxy(
					GatheringByteChannel.class));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Scattering byte channel is not of type SelectableChannel",
				iae.getMessage());
		}

		// Gathering byte channel is not of type SelectableChannel

		try {
			_selectorIntraBand.registerChannel(
				new MockDuplexSelectableChannel(false, false),
				IntraBandTestUtil.<GatheringByteChannel>createProxy(
					GatheringByteChannel.class));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Gathering byte channel is not of type SelectableChannel",
				iae.getMessage());
		}

		// Scattering byte channel is not valid for reading

		try {
			_selectorIntraBand.registerChannel(
				new MockDuplexSelectableChannel(false, true),
				new MockDuplexSelectableChannel(true, true));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Scattering byte channel is not valid for reading",
				iae.getMessage());
		}

		// Gathering byte channel is not valid for writing

		try {
			_selectorIntraBand.registerChannel(
				new MockDuplexSelectableChannel(true, true),
				new MockDuplexSelectableChannel(true, false));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Gathering byte channel is not valid for writing",
				iae.getMessage());
		}

		// Interruptted on register

		Pipe pipe = Pipe.open();

		SourceChannel sourceChannel = pipe.source();
		SinkChannel sinkChannel = pipe.sink();

		final Thread mainThread = Thread.currentThread();

		Thread wakeUpThread = new Thread(
			new WakeUpRunnable(_selectorIntraBand));

		Thread interruptThread = new Thread() {

			@Override
			public void run() {
				while (mainThread.getState() != Thread.State.WAITING);

				mainThread.interrupt();
			}

		};

		wakeUpThread.start();

		Selector selector = _selectorIntraBand.selector;

		synchronized (selector) {
			wakeUpThread.interrupt();
			wakeUpThread.join();

			interruptThread.start();

			try {
				_selectorIntraBand.registerChannel(sourceChannel, sinkChannel);

				Assert.fail();
			}
			catch (IOException ioe) {
				Throwable cause = ioe.getCause();

				Assert.assertTrue(cause instanceof InterruptedException);
			}

			interruptThread.join();
		}

		// Normal register

		SelectionKeyRegistrationReference selectionKeyRegistrationReference =
			(SelectionKeyRegistrationReference)
				_selectorIntraBand.registerChannel(sourceChannel, sinkChannel);

		Assert.assertNotNull(selectionKeyRegistrationReference);

		SelectionKey readSelectionKey =
			selectionKeyRegistrationReference.readSelectionKey;

		Assert.assertTrue(readSelectionKey.isValid());
		Assert.assertEquals(
			SelectionKey.OP_READ, readSelectionKey.interestOps());
		Assert.assertNotNull(readSelectionKey.attachment());

		SelectionKey writeSelectionKey =
			selectionKeyRegistrationReference.writeSelectionKey;

		Assert.assertTrue(writeSelectionKey.isValid());
		Assert.assertEquals(
			SelectionKey.OP_WRITE, writeSelectionKey.interestOps());
		Assert.assertNotNull(writeSelectionKey.attachment());
		Assert.assertSame(
			readSelectionKey.attachment(), writeSelectionKey.attachment());

		unregisterChannels(selectionKeyRegistrationReference);

		// Register after close

		_selectorIntraBand.close();

		try {
			_selectorIntraBand.registerChannel(sourceChannel, sinkChannel);

			Assert.fail();
		}
		catch (ClosedIntraBandException cibe) {
		}

		sourceChannel.close();
		sinkChannel.close();
	}

	@AdviseWith(adviceClasses={Jdk14LogImplAdvice.class})
	@Test
	public void testSendDatagramWithCallback() throws Exception {

		// Submitted callback

		Pipe readPipe = Pipe.open();
		Pipe writePipe = Pipe.open();

		GatheringByteChannel gatheringByteChannel = writePipe.sink();
		ScatteringByteChannel scatteringByteChannel = readPipe.source();

		RegistrationReference registrationReference =
			_selectorIntraBand.registerChannel(
				writePipe.source(), readPipe.sink());

		Object attachment = new Object();

		RecordCompletionHandler<Object> recordCompletionHandler =
			new RecordCompletionHandler<Object>();

		_selectorIntraBand.sendDatagram(
			registrationReference, Datagram.createRequestDatagram(_type, _data),
			attachment, EnumSet.of(CompletionType.SUBMITTED),
			recordCompletionHandler);

		Datagram receiveDatagram = readDatagramFully(scatteringByteChannel);

		recordCompletionHandler.waitUntilSubmitted();

		Assert.assertSame(attachment, recordCompletionHandler.getAttachment());
		Assert.assertEquals(_type, receiveDatagram.getType());

		ByteBuffer dataByteBuffer = receiveDatagram.getDataByteBuffer();

		Assert.assertArrayEquals(_data, dataByteBuffer.array());

		// Callback timeout, with log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.WARNING);

		recordCompletionHandler = new RecordCompletionHandler<Object>();

		_selectorIntraBand.sendDatagram(
			registrationReference, Datagram.createRequestDatagram(_type, _data),
			attachment, EnumSet.of(CompletionType.DELIVERED),
			recordCompletionHandler, 10, TimeUnit.MILLISECONDS);

		Selector selector = _selectorIntraBand.selector;

		recordCompletionHandler.waitUntilTimeouted(selector);

		Assert.assertSame(attachment, recordCompletionHandler.getAttachment());
		Assert.assertEquals(1, logRecords.size());

		assertMessageStartWith(
			logRecords.get(0), "Removed timeout response waiting datagram");

		// Callback timeout, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		recordCompletionHandler = new RecordCompletionHandler<Object>();

		_selectorIntraBand.sendDatagram(
			registrationReference, Datagram.createRequestDatagram(_type, _data),
			attachment, EnumSet.of(CompletionType.DELIVERED),
			recordCompletionHandler, 10, TimeUnit.MILLISECONDS);

		recordCompletionHandler.waitUntilTimeouted(selector);

		Assert.assertSame(attachment, recordCompletionHandler.getAttachment());
		Assert.assertTrue(logRecords.isEmpty());

		// Callback timeout, completion handler causes NPE

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			SelectorIntraBand.class.getName(), Level.SEVERE);

		recordCompletionHandler = new RecordCompletionHandler<Object>() {

			@Override
			public void timeouted(Object attachment) {
				super.timeouted(attachment);

				throw new NullPointerException();
			}

		};

		Jdk14LogImplAdvice.reset();

		try {
			_selectorIntraBand.sendDatagram(
				registrationReference,
				Datagram.createRequestDatagram(_type, _data), attachment,
				EnumSet.of(CompletionType.DELIVERED), recordCompletionHandler,
				10, TimeUnit.MILLISECONDS);
		}
		finally {
			recordCompletionHandler.waitUntilTimeouted(selector);

			Jdk14LogImplAdvice.waitUntilErrorCalled();
		}

		Assert.assertFalse(selector.isOpen());
		Assert.assertEquals(1, logRecords.size());

		assertMessageStartWith(
			logRecords.get(0),
			SelectorIntraBand.class + ".threadFactory-1 exiting exceptionally");

		gatheringByteChannel.close();
		scatteringByteChannel.close();
	}

	@Aspect
	public static class Jdk14LogImplAdvice {

		public static void reset() {
			_errorCalledCountDownLatch = new CountDownLatch(1);
			_isWarnEnabledCalledCountDownLatch = new CountDownLatch(1);
			_warnCalledCountDownLatch = new CountDownLatch(1);
		}

		public static void waitUntilErrorCalled() throws InterruptedException {
			_errorCalledCountDownLatch.await();
		}

		public static void waitUntilIsWarnEnableCalled()
			throws InterruptedException {

			_isWarnEnabledCalledCountDownLatch.await();
		}

		public static void waitUntilWarnCalled() throws InterruptedException {
			_warnCalledCountDownLatch.await();
		}

		@org.aspectj.lang.annotation.After(
			"execution(* com.liferay.portal.kernel.log.Jdk14LogImpl.error(" +
				"Object, Throwable))")
		public void error() {
			_errorCalledCountDownLatch.countDown();
		}

		@org.aspectj.lang.annotation.After(
			"execution(* com.liferay.portal.kernel.log.Jdk14LogImpl." +
				"isWarnEnabled())")
		public void isWarnEnabled() {
			_isWarnEnabledCalledCountDownLatch.countDown();
		}

		@org.aspectj.lang.annotation.After(
			"execution(* com.liferay.portal.kernel.log.Jdk14LogImpl.warn(" +
				"Object))")
		public void warn1() {
			_warnCalledCountDownLatch.countDown();
		}

		@org.aspectj.lang.annotation.After(
			"execution(* com.liferay.portal.kernel.log.Jdk14LogImpl.warn(" +
				"Object, Throwable))")
		public void warn2() {
			_warnCalledCountDownLatch.countDown();
		}

		public static volatile CountDownLatch _errorCalledCountDownLatch =
			new CountDownLatch(1);
		public static volatile CountDownLatch
			_isWarnEnabledCalledCountDownLatch = new CountDownLatch(1);
		public static volatile CountDownLatch
			_warnCalledCountDownLatch = new CountDownLatch(1);

	}

	protected void assertMessageStartWith(
		LogRecord logRecord, String messagePrefix) {

		String message = logRecord.getMessage();

		Assert.assertTrue(message.startsWith(messagePrefix));
	}

	protected Datagram readDatagramFully(
			ScatteringByteChannel scatteringByteChannel)
		throws IOException {

		Datagram datagram = DatagramHelper.createReceiveDatagram();

		while (!DatagramHelper.readFrom(datagram, scatteringByteChannel));

		return datagram;
	}

	void unregisterChannels(
			SelectionKeyRegistrationReference registrationReference)
		throws Exception {

		registrationReference.cancelRegistration();

		SelectorIntraBand defaultIntraBand =
			(SelectorIntraBand)registrationReference.getIntraBand();

		Selector selector = defaultIntraBand.selector;

		Set<SelectionKey> keys = selector.keys();

		while (!keys.isEmpty()) {
			selector.wakeup();
		}

		SelectionKey readSelectionKey = registrationReference.readSelectionKey;
		SelectionKey writeSelectionKey =
			registrationReference.writeSelectionKey;

		SelectableChannel readSelectableChannel = readSelectionKey.channel();
		SelectableChannel writeSelectableChannel = writeSelectionKey.channel();

		while (readSelectableChannel.keyFor(selector) != null);
		while (writeSelectableChannel.keyFor(selector) != null);

		writeSelectableChannel.close();
		readSelectableChannel.close();
	}

	private static final String _DATA_STRING =
		SelectorIntraBandTest.class.getName();

	private static final long _DEFAULT_TIMEOUT = Time.SECOND;

	private byte[] _data = _DATA_STRING.getBytes(Charset.defaultCharset());

	private SelectorIntraBand _selectorIntraBand;

	private byte _type = 1;

	private static class MockDuplexSelectableChannel
		extends SelectableChannel
		implements GatheringByteChannel, ScatteringByteChannel {

		public MockDuplexSelectableChannel(boolean readable, boolean writable) {
			_readable = readable;
			_writable = writable;
		}

		@Override
		public SelectorProvider provider() {
			throw new UnsupportedOperationException();
		}

		@Override
		public int validOps() {
			int ops = 0;

			if (_readable) {
				ops |= SelectionKey.OP_READ;
			}

			if (_writable) {
				ops |= SelectionKey.OP_WRITE;
			}

			return ops;
		}

		@Override
		public boolean isRegistered() {
			throw new UnsupportedOperationException();
		}

		@Override
		public SelectionKey keyFor(Selector selector) {
			throw new UnsupportedOperationException();
		}

		@Override
		public SelectionKey register(
			Selector selector, int ops, Object attachment) {

			throw new UnsupportedOperationException();
		}

		@Override
		public SelectableChannel configureBlocking(boolean block) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isBlocking() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Object blockingLock() {
			throw new UnsupportedOperationException();
		}

		@Override
		protected void implCloseChannel() {
			throw new UnsupportedOperationException();
		}

		public long read(ByteBuffer[] byteBuffers, int offset, int length) {
			throw new UnsupportedOperationException();
		}

		public long read(ByteBuffer[] byteBuffers) {
			throw new UnsupportedOperationException();
		}

		public int read(ByteBuffer byteBuffer) {
			throw new UnsupportedOperationException();
		}

		public long write(ByteBuffer[] byteBuffers, int offset, int length) {
			throw new UnsupportedOperationException();
		}

		public long write(ByteBuffer[] byteBuffers) {
			throw new UnsupportedOperationException();
		}

		public int write(ByteBuffer byteBuffer) {
			throw new UnsupportedOperationException();
		}

		private boolean _readable;
		private boolean _writable;

	}

	private class WakeUpRunnable implements Runnable {

		public WakeUpRunnable(SelectorIntraBand selectorIntraBand) {
			_selectorIntraBand = selectorIntraBand;
		}

		public void run() {
			Thread currentThread = Thread.currentThread();

			while (!currentThread.isInterrupted()) {
				_selectorIntraBand.selector.wakeup();
			}
		}

		private final SelectorIntraBand _selectorIntraBand;

	}

}