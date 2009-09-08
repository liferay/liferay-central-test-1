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

package com.liferay.portal.scheduler.trigger;

import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.trigger.Trigger;
import com.liferay.portal.kernel.scheduler.trigger.TriggerFactory;
import com.liferay.portal.kernel.scheduler.trigger.TriggerType;

import java.util.Date;

/**
 * <a href="TriggerFactoryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class TriggerFactoryImpl implements TriggerFactory {

	public Trigger buildTrigger(
		TriggerType triggerType, String jobName, String groupName,
		Date startDate, Date endDate, Object triggerContent)
		throws SchedulerException {

		switch (triggerType) {
			case CRON:
				if (!String.class.isAssignableFrom(triggerContent.getClass())) {
					throw new SchedulerException(
						"Cron type trigger needs String type triggerContent, " +
						"but found " + triggerContent.getClass());
				}
				String cronText = (String) triggerContent;
				return new CronTrigger(
					jobName, groupName, startDate, endDate, cronText);
			case SIMPLE:
				if (!Number.class.isAssignableFrom(triggerContent.getClass())) {
					throw new SchedulerException(
						"Cron type trigger needs Number type triggerContent, " +
						"but found " + triggerContent.getClass());
				}
				Number number = (Number) triggerContent;
				return new IntervalTrigger(
					jobName, groupName, startDate, endDate, number.longValue());
			default:
				throw new SchedulerException(
					"Unknow trigger type:" + triggerType);
		}
	}

}