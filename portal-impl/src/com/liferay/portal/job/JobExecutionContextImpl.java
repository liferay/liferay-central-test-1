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

package com.liferay.portal.job;

import java.util.Date;

import org.quartz.JobExecutionContext;

/**
 * <a href="JobExecutionContextImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class JobExecutionContextImpl
	implements com.liferay.portal.kernel.job.JobExecutionContext {

	public JobExecutionContextImpl(JobExecutionContext context) {
		_context = context;
	}

	public Object get(Object key) {
		return _context.get(key);
	}

	public long getJobRunTime() {
		return _context.getJobRunTime();
	}

	public Date getNextFireTime() {
		return _context.getNextFireTime();
	}

	public Date getPreviousFireTime() {
		return _context.getPreviousFireTime();
	}

	public Object getResult() {
		return _context.getResult();
	}

	public void put(Object key, Object value) {
		_context.put(key, value);
	}

	public void setJobRunTime(long jobRunTime) {
		_context.setJobRunTime(jobRunTime);
	}

	public void setResult(Object result) {
		_context.setResult(result);
	}

	private JobExecutionContext _context;

}