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

package com.liferay.portal.lar;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.ratings.model.RatingsEntry;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <a href="PortletDataContext.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * Holds context information that is used during exporting and importing portlet
 * data.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 *
 */
public interface PortletDataContext extends Serializable {

	public static final String ROOT_PATH_GROUPS = "/groups/";

	public static final String ROOT_PATH_LAYOUTS = "/layouts/";

	public static final String ROOT_PATH_PORTLETS = "/portlets/";

	public void addComments(Class<?> classObj, long classPK)
		throws SystemException;

	public void addComments(
		String className, long classPK, List<MBMessage> messages);

	public boolean addPrimaryKey(Class<?> classObj, String primaryKey);

	public void addRatingsEntries(Class<?> classObj, long classPK)
		throws SystemException;

	public void addRatingsEntries(
		String className, long classPK, List<RatingsEntry> entries);

	public void addTagsEntries(Class<?> classObj, long classPK)
		throws PortalException, SystemException;

	public void addTagsEntries(String className, long classPK, String[] values);

	public void addZipEntry(String path, byte[] bytes) throws SystemException;

	public void addZipEntry(String path, Object object) throws SystemException;

	public void addZipEntry(String path, String s) throws SystemException;

	public void addZipEntry(String name, StringBuilder sb)
		throws SystemException;

	public Object fromXML(byte[] bytes);

	public Object fromXML(String xml);

	public boolean getBooleanParameter(String namespace, String name);

	public Map<String, List<MBMessage>> getComments();

	public long getCompanyId();

	public String getDataStrategy();

	public Date getEndDate();

	public long getGroupId();

	public long getImportGroupId();

	public String getImportLayoutPath(long layoutId);

	public String getImportPortletPath(String portletId);

	public String getImportRootPath();

	public String getLayoutPath(long layoutId);

	public Map<?, ?> getNewPrimaryKeysMap(Class<?> classObj);

	public long getOldPlid();

	public Map<String, String[]> getParameterMap();

	public long getPlid();

	public String getPortletPath(String portletId);

	public Set<String> getPrimaryKeys();

	public Map<String, List<RatingsEntry>> getRatingsEntries();

	public String getRootPath();

	public Date getStartDate();

	public Map<String, String[]> getTagsEntries();

	public String[] getTagsEntries(Class<?> classObj, long classPK);

	public String[] getTagsEntries(String className, long classPK);

	public long getUserId(String userUuid) throws SystemException;

	public UserIdStrategy getUserIdStrategy() throws SystemException;

	public Map<String, byte[]> getZipEntries();

	public byte[] getZipEntryAsByteArray(String path);

	public Object getZipEntryAsObject(String path);

	public String getZipEntryAsString(String path);

	public Map<String, List<ObjectValuePair<String, byte[]>>>
		getZipFolderEntries();

	public List<ObjectValuePair<String, byte[]>> getZipFolderEntries(
		String path);

	public ZipReader getZipReader();

	public ZipWriter getZipWriter();

	public boolean hasDateRange();

	public boolean hasNotUniquePerLayout(String portletId);

	public boolean hasPrimaryKey(Class<?> classObj, String primaryKey);

	public void importComments(
			Class<?> classObj, long classPK, long newClassPK, long groupId)
		throws PortalException, SystemException;

	public void importRatingsEntries(
			Class<?> classObj, long classPK, long newClassPK)
		throws PortalException, SystemException;

	public boolean isPathNotProcessed(String path);

	public boolean isWithinDateRange(Date modifiedDate);

	public void putNotUniquePerLayout(String portletId);

	public void setImportGroupId(long importGroupId);

	public void setOldPlid(long oldPlid);

	public void setPlid(long plid);

	public String toXML(Object object);

}