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

package com.liferay.portal.monitoring.statistics.portlet;

import com.liferay.portal.monitoring.MonitoringException;
import com.liferay.portal.monitoring.statistics.RequestStatistics;

import java.util.Set;

/**
 * <a href="RenderRequestSummaryStatistics.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 *
 */
public class RenderRequestSummaryStatistics
	implements PortletSummaryStatistics {

	public RenderRequestSummaryStatistics(ServerStatistics serverStatistics) {
		_serverStatistics = serverStatistics;
	}

	public long getAverageTime() {
		long averageTime = 0;

		long count = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			for (RequestStatistics requestStatistics :
					companyStatistics.getRenderRequestStatisticsSet()) {

				averageTime += requestStatistics.getAverageTime();

				count++;
			}
		}

		return averageTime / count;
	}

	public long getAverageTimeByCompany(long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getAverageTimeByCompany(companyStatistics);
	}

	public long getAverageTimeByCompany(String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getAverageTimeByCompany(companyStatistics);
	}

	public long getAverageTimeByPortlet(String portletId)
		throws MonitoringException {

		long averageTime = 0;

		Set<CompanyStatistics> companyStatisticsSet =
			_serverStatistics.getCompanyStatisticsSet();

		for (CompanyStatistics companyStatistics : companyStatisticsSet) {
			RequestStatistics requestStatistics =
				companyStatistics.getRenderRequestStatistics(portletId);

			averageTime += requestStatistics.getAverageTime();
		}

		return averageTime / companyStatisticsSet.size();
	}

	public long getAverageTimeByPortlet(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		RequestStatistics requestStatistics =
			companyStatistics.getRenderRequestStatistics(portletId);

		return requestStatistics.getAverageTime();
	}

	public long getAverageTimeByPortlet(String portletId, String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		RequestStatistics requestStatistics =
			companyStatistics.getRenderRequestStatistics(portletId);

		return requestStatistics.getAverageTime();
	}

	public long getErrorCount() {
		long errorCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			errorCount += getErrorCountByCompany(companyStatistics);
		}

		return errorCount;
	}

	public long getErrorCountByCompany(long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getErrorCountByCompany(companyStatistics);
	}

	public long getErrorCountByCompany(String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getErrorCountByCompany(companyStatistics);
	}

	public long getErrorCountByPortlet(String portletId)
		throws MonitoringException {

		long errorCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			errorCount += getErrorCountByPortlet(portletId, companyStatistics);
		}

		return errorCount;
	}

	public long getErrorCountByPortlet(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getErrorCountByPortlet(portletId, companyStatistics);
	}

	public long getErrorCountByPortlet(String portletId, String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getErrorCountByPortlet(portletId, companyStatistics);
	}

	public long getMaxTime() {
		long maxTime = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			for (RequestStatistics requestStatistics :
					companyStatistics.getRenderRequestStatisticsSet()) {

				if (requestStatistics.getMaxTime() > maxTime) {
					maxTime = requestStatistics.getMaxTime();
				}
			}
		}

		return maxTime;
	}

	public long getMaxTimeByCompany(long companyId) throws MonitoringException {
		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return companyStatistics.getMaxTime();
	}

	public long getMaxTimeByCompany(String webId) throws MonitoringException {
		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return companyStatistics.getMaxTime();
	}

	public long getMaxTimeByPortlet(String portletId)
		throws MonitoringException {

		long maxTime = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			long curMaxTime = getMaxTimeByPortlet(portletId, companyStatistics);

			if (curMaxTime > maxTime) {
				maxTime = curMaxTime;
			}
		}

		return maxTime;
	}

	public long getMaxTimeByPortlet(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getMaxTimeByPortlet(portletId, companyStatistics);
	}

	public long getMaxTimeByPortlet(String portletId, String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getMaxTimeByPortlet(portletId, companyStatistics);
	}

	public long getMinTime() {
		long minTime = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			for (RequestStatistics requestStatistics :
					companyStatistics.getRenderRequestStatisticsSet()) {

				if (requestStatistics.getMinTime() < minTime) {
					minTime = requestStatistics.getMinTime();
				}
			}
		}

		return minTime;
	}

	public long getMinTimeByCompany(long companyId) throws MonitoringException {
		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return companyStatistics.getMinTime();
	}

	public long getMinTimeByCompany(String webId) throws MonitoringException {
		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return companyStatistics.getMinTime();
	}

	public long getMinTimeByPortlet(String portletId)
		throws MonitoringException {

		long minTime = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			long curMinTime = getMinTimeByPortlet(portletId, companyStatistics);

			if (curMinTime < minTime) {
				minTime = curMinTime;
			}
		}

		return minTime;
	}

	public long getMinTimeByPortlet(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getMinTimeByPortlet(portletId, companyStatistics);
	}

	public long getMinTimeByPortlet(String portletId, String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getMinTimeByPortlet(portletId, companyStatistics);
	}

	public long getRequestCount() {
		long requestCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			requestCount += getRequestCountByCompany(companyStatistics);
		}

		return requestCount;
	}

	public long getRequestCountByCompany(long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getRequestCountByCompany(companyStatistics);
	}

	public long getRequestCountByCompany(String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getRequestCountByCompany(companyStatistics);
	}

	public long getRequestCountByPortlet(String portletId)
		throws MonitoringException {

		long requestCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			requestCount += getRequestCountByPortlet(
				portletId, companyStatistics);
		}

		return requestCount;
	}

	public long getRequestCountByPortlet(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getRequestCountByPortlet(portletId, companyStatistics);
	}

	public long getRequestCountByPortlet(String portletId, String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getRequestCountByPortlet(portletId, companyStatistics);
	}

	public long getSuccessCount() {
		long successCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			successCount += getSuccessCountByCompany(companyStatistics);
		}

		return successCount;
	}

	public long getSuccessCountByCompany(long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getSuccessCountByCompany(companyStatistics);
	}

	public long getSuccessCountByCompany(String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getSuccessCountByCompany(companyStatistics);
	}

	public long getSuccessCountByPortlet(String portletId)
		throws MonitoringException {

		long successCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			successCount += getSuccessCountByPortlet(
				portletId, companyStatistics);
		}

		return successCount;
	}

	public long getSuccessCountByPortlet(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getSuccessCountByPortlet(portletId, companyStatistics);
	}

	public long getSuccessCountByPortlet(String portletId, String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getSuccessCountByPortlet(portletId, companyStatistics);
	}

	public long getTimeoutCount() {
		long timeoutCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			timeoutCount += getTimeoutCountByCompany(companyStatistics);
		}

		return timeoutCount;
	}

	public long getTimeoutCountByCompany(long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getTimeoutCountByCompany(companyStatistics);
	}

	public long getTimeoutCountByCompany(String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getTimeoutCountByCompany(companyStatistics);
	}

	public long getTimeoutCountByPortlet(String portletId)
		throws MonitoringException {

		long timeoutCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			timeoutCount += getTimeoutCountByPortlet(
				portletId, companyStatistics);
		}

		return timeoutCount;
	}

	public long getTimeoutCountByPortlet(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getTimeoutCountByPortlet(portletId, companyStatistics);
	}

	public long getTimeoutCountByPortlet(String portletId, String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getTimeoutCountByPortlet(portletId, companyStatistics);
	}

	protected long getAverageTimeByCompany(
		CompanyStatistics companyStatistics) {

		long averageTime = 0;

		Set<RequestStatistics> requestStatisticsSet =
			companyStatistics.getRenderRequestStatisticsSet();

		for (RequestStatistics requestStatistics : requestStatisticsSet) {
			averageTime += requestStatistics.getAverageTime();
		}

		return averageTime / requestStatisticsSet.size();
	}

	protected long getErrorCountByCompany(CompanyStatistics companyStatistics) {
		long errorCount = 0;

		for (RequestStatistics requestStatistics :
				companyStatistics.getRenderRequestStatisticsSet()) {

			errorCount += requestStatistics.getErrorCount();
		}

		return errorCount;
	}

	protected long getErrorCountByPortlet(
			String portletId, CompanyStatistics companyStatistics)
		throws MonitoringException {

		RequestStatistics requestStatistics =
			companyStatistics.getRenderRequestStatistics(portletId);

		return requestStatistics.getErrorCount();
	}

	protected long getMaxTimeByPortlet(
			String portletId, CompanyStatistics companyStatistics)
		throws MonitoringException {

		long maxTime = 0;

		RequestStatistics requestStatistics =
			companyStatistics.getRenderRequestStatistics(portletId);

		if (requestStatistics.getMaxTime() > maxTime) {
			maxTime = requestStatistics.getMaxTime();
		}

		return maxTime;
	}

	protected long getMinTimeByPortlet(
			String portletId, CompanyStatistics companyStatistics)
		throws MonitoringException {

		long minTime = 0;

		RequestStatistics requestStatistics =
			companyStatistics.getRenderRequestStatistics(portletId);

		if (requestStatistics.getMinTime() < minTime) {
			minTime = requestStatistics.getMinTime();
		}

		return minTime;
	}

	protected long getRequestCountByCompany(
		CompanyStatistics companyStatistics) {

		long requestCount = 0;

		for (RequestStatistics requestStatistics :
				companyStatistics.getRenderRequestStatisticsSet()) {

			requestCount += requestStatistics.getRequestCount();
		}

		return requestCount;
	}

	protected long getRequestCountByPortlet(
			String portletId, CompanyStatistics companyStatistics)
		throws MonitoringException {

		RequestStatistics requestStatistics =
			companyStatistics.getRenderRequestStatistics(portletId);

		return requestStatistics.getRequestCount();
	}

	protected long getSuccessCountByCompany(
		CompanyStatistics companyStatistics) {

		long successCount = 0;

		for (RequestStatistics requestStatistics :
				companyStatistics.getRenderRequestStatisticsSet()) {

			successCount += requestStatistics.getSuccessCount();
		}

		return successCount;
	}

	protected long getSuccessCountByPortlet(
			String portletId, CompanyStatistics companyStatistics)
		throws MonitoringException {

		RequestStatistics requestStatistics =
			companyStatistics.getRenderRequestStatistics(portletId);

		return requestStatistics.getSuccessCount();
	}

	protected long getTimeoutCountByCompany(
		CompanyStatistics companyStatistics) {

		long timeoutCount = 0;

		for (RequestStatistics requestStatistics :
				companyStatistics.getRenderRequestStatisticsSet()) {

			timeoutCount += requestStatistics.getTimeoutCount();
		}

		return timeoutCount;
	}

	protected long getTimeoutCountByPortlet(
			String portletId, CompanyStatistics companyStatistics)
		throws MonitoringException {

		RequestStatistics requestStatistics =
			companyStatistics.getRenderRequestStatistics(portletId);

		return requestStatistics.getTimeoutCount();
	}

	private ServerStatistics _serverStatistics;

}