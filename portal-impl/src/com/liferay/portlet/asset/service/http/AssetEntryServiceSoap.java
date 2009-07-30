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

package com.liferay.portlet.asset.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.asset.service.AssetEntryServiceUtil;

import java.rmi.RemoteException;

/**
 * <a href="AssetEntryServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a SOAP utility for the {@link
 * com.liferay.portlet.asset.service.AssetEntryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link
 * com.liferay.portlet.asset.model.AssetEntrySoap}. If the method in the service
 * utility returns a {@link com.liferay.portlet.asset.model.AssetEntry}, that is
 * translated to a {@link com.liferay.portlet.asset.model.AssetEntrySoap}.
 * Methods that SOAP cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at
 * http://localhost:8080/tunnel-web/secure/axis. Set the property
 * <b>tunnel.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    AssetEntryServiceHttp
 * @see    com.liferay.portlet.asset.model.AssetEntrySoap
 * @see    com.liferay.portlet.asset.service.AssetEntryServiceUtil
 */
public class AssetEntryServiceSoap {
	public static void deleteEntry(long entryId) throws RemoteException {
		try {
			AssetEntryServiceUtil.deleteEntry(entryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetEntrySoap[] getCompanyEntries(
		long companyId, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetEntry> returnValue =
				AssetEntryServiceUtil.getCompanyEntries(companyId, start, end);

			return com.liferay.portlet.asset.model.AssetEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCompanyEntriesCount(long companyId)
		throws RemoteException {
		try {
			int returnValue = AssetEntryServiceUtil.getCompanyEntriesCount(companyId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String getCompanyEntriesRSS(long companyId,
		int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String tagURL) throws RemoteException {
		try {
			java.lang.String returnValue = AssetEntryServiceUtil.getCompanyEntriesRSS(companyId,
					max, type, version, displayStyle, feedURL, tagURL);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetEntryDisplay[] getCompanyEntryDisplays(
		long companyId, int start, int end, java.lang.String languageId)
		throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetEntryDisplay[] returnValue = AssetEntryServiceUtil.getCompanyEntryDisplays(companyId,
					start, end, languageId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetEntrySoap[] getEntries(
		com.liferay.portlet.asset.service.persistence.AssetEntryQuery entryQuery)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetEntry> returnValue =
				AssetEntryServiceUtil.getEntries(entryQuery);

			return com.liferay.portlet.asset.model.AssetEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getEntriesCount(
		com.liferay.portlet.asset.service.persistence.AssetEntryQuery entryQuery)
		throws RemoteException {
		try {
			int returnValue = AssetEntryServiceUtil.getEntriesCount(entryQuery);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String getEntriesRSS(
		com.liferay.portlet.asset.service.persistence.AssetEntryQuery entryQuery,
		java.lang.String type, double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String tagURL)
		throws RemoteException {
		try {
			java.lang.String returnValue = AssetEntryServiceUtil.getEntriesRSS(entryQuery,
					type, version, displayStyle, feedURL, tagURL);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetEntrySoap getEntry(
		long entryId) throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetEntry returnValue = AssetEntryServiceUtil.getEntry(entryId);

			return com.liferay.portlet.asset.model.AssetEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetEntryType[] getEntryTypes(
		java.lang.String languageId) throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetEntryType[] returnValue = AssetEntryServiceUtil.getEntryTypes(languageId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetEntrySoap incrementViewCounter(
		java.lang.String className, long classPK) throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetEntry returnValue = AssetEntryServiceUtil.incrementViewCounter(className,
					classPK);

			return com.liferay.portlet.asset.model.AssetEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetEntryDisplay[] searchEntryDisplays(
		long companyId, java.lang.String portletId, java.lang.String keywords,
		java.lang.String languageId, int start, int end)
		throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetEntryDisplay[] returnValue = AssetEntryServiceUtil.searchEntryDisplays(companyId,
					portletId, keywords, languageId, start, end);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int searchEntryDisplaysCount(long companyId,
		java.lang.String portletId, java.lang.String keywords,
		java.lang.String languageId) throws RemoteException {
		try {
			int returnValue = AssetEntryServiceUtil.searchEntryDisplaysCount(companyId,
					portletId, keywords, languageId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetEntrySoap updateEntry(
		long groupId, java.lang.String className, long classPK,
		long[] categoryIds, java.lang.String[] tagNames, boolean visible,
		java.util.Date startDate, java.util.Date endDate,
		java.util.Date publishDate, java.util.Date expirationDate,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String summary,
		java.lang.String url, int height, int width,
		java.lang.Integer priority, boolean sync) throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetEntry returnValue = AssetEntryServiceUtil.updateEntry(groupId,
					className, classPK, categoryIds, tagNames, visible,
					startDate, endDate, publishDate, expirationDate, mimeType,
					title, description, summary, url, height, width, priority,
					sync);

			return com.liferay.portlet.asset.model.AssetEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AssetEntryServiceSoap.class);
}