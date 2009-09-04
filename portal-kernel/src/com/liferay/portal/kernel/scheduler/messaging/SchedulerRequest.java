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

package com.liferay.portal.kernel.scheduler.messaging;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.trigger.Trigger;

import java.io.Serializable;

/**
 * <a href="SchedulerRequest.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * A request to schedule a job for the scheduling engine. You may specify the
 * timing of the job via the cron syntax. See
 * http://quartz.sourceforge.net/javadoc/org/quartz/CronTrigger.html for a
 * description of the syntax.
 * </p>
 *
 * @author Michael C. Han
 * @author Bruno Farache
 * @author Brian Wing Shun Chan
 */
public class SchedulerRequest implements Serializable {

	public static final String COMMAND_REGISTER = "REGISTER";

	public static final String COMMAND_RETRIEVE = "RETRIEVE";

	public static final String COMMAND_SHUTDOWN = "SHUTDOWN";

	public static final String COMMAND_STARTUP = "STARTUP";

	public static final String COMMAND_UNREGISTER = "UNREGISTER";

	public static SchedulerRequest createRegisterRequest(
		Trigger trigger, String description, String destination,
		Message message) {

		return new SchedulerRequest(
			COMMAND_REGISTER, trigger, description, destination, message);
	}

	public static SchedulerRequest createRetrieveRequest(Trigger trigger) {
		return new SchedulerRequest(
			COMMAND_RETRIEVE, trigger, null, null, null);
	}

	public static SchedulerRequest createRetrieveResponseRequest(
		Trigger trigger, String description, Message message) {

		return new SchedulerRequest(null, trigger, description, null, message);
	}

	public static SchedulerRequest createShutdownRequest() {
		return new SchedulerRequest(COMMAND_SHUTDOWN, null, null, null, null);
	}

	public static SchedulerRequest createStartupRequest() {
		return new SchedulerRequest(COMMAND_STARTUP, null, null, null, null);
	}

	public static SchedulerRequest createUnregisterRequest(Trigger trigger) {
		return new SchedulerRequest(
			COMMAND_UNREGISTER, trigger, null, null, null);
	}

	private SchedulerRequest(
		String command, Trigger trigger, String description, String destination,
		Message message) {

		_command = command;
		_trigger = trigger;
		_description = description;
		_destination = destination;
		_message = message;
	}

	public String getCommand() {
		return _command;
	}

	public String getDescription() {
		return _description;
	}

	public String getDestination() {
		return _destination;
	}

	public Trigger getTrigger() {
		return _trigger;
	}

	public Message getMessage() {
		return _message;
	}

	public void setCommand(String command) {
		_command = command;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setDestination(String destination) {
		_destination = destination;
	}

	public void setTrigger(Trigger trigger) {
		_trigger = trigger;
	}

	public void setMessage(Message message) {
		_message = message;
	}

	@Override
	public String toString() {
		return "SchedulerRequest[" +
			"command:" + _command +
			", description:" + _description +
			", destination:" + _destination +
			", trigger:" + _trigger +
			", message:" + _message +
			"]";
	}

	private String _command;
	private String _description;
	private String _destination;
	private Trigger _trigger;
	private Message _message;

}