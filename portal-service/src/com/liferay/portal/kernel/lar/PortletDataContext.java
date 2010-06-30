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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.model.Lock;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.ratings.model.RatingsEntry;

import java.io.InputStream;
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
 */
public interface PortletDataContext extends Serializable {

	public static final String ROOT_PATH_GROUPS = "/groups/";

	public static final String ROOT_PATH_LAYOUTS = "/layouts/";

	public static final String ROOT_PATH_PORTLETS = "/portlets/";

	public void addAssetCategories(Class<?> classObj, long classPK)
		throws SystemException;

	public void addAssetCategories(
		String className, long classPK, long[] assetCategoryIds);

	public void addAssetTags(Class<?> classObj, long classPK)
		throws SystemException;

	public void addAssetTags(
		String className, long classPK, String[] assetTagNames);

	public void addComments(Class<?> classObj, long classPK)
		throws SystemException;

	public void addComments(
		String className, long classPK, List<MBMessage> messages);

	public void addLocks(Class<?> classObj, String key)
		throws PortalException, SystemException;

	public void addLocks(String className, String key, Lock lock);

	public void addPermissions(Class<?> classObj, long classPK)
		throws PortalException, SystemException;

	public void addPermissions(String resourceName, long resourcePK)
		throws PortalException, SystemException;

	public void addPermissions(
		String resourceName, long resourcePK, List<KeyValuePair> permissions);

	public boolean addPrimaryKey(Class<?> classObj, String primaryKey);

	public void addRatingsEntries(Class<?> classObj, long classPK)
		throws SystemException;

	public void addRatingsEntries(
		String className, long classPK, List<RatingsEntry> ratingsEntries);

	public void addZipEntry(String path, byte[] bytes) throws SystemException;

	public void addZipEntry(String path, InputStream is) throws SystemException;

	public void addZipEntry(String path, Object object) throws SystemException;

	public void addZipEntry(String path, String s) throws SystemException;

	public void addZipEntry(String name, StringBuilder sb)
		throws SystemException;

	public Object fromXML(byte[] bytes);

	public Object fromXML(String xml);

	public long[] getAssetCategoryIds(Class<?> classObj, long classPK);

	public Map<String, long[]> getAssetCategoryIdsMap();

	public Map<String, String[]> getAssetCategoryUuidsMap();

	public String[] getAssetTagNames(Class<?> classObj, long classPK);

	public String[] getAssetTagNames(String className, long classPK);

	public Map<String, String[]> getAssetTagNamesMap();

	public boolean getBooleanParameter(String namespace, String name);

	public ClassLoader getClassLoader();

	public Map<String, List<MBMessage>> getComments();

	public long getCompanyId();

	public String getDataStrategy();

	public Date getEndDate();

	public long getGroupId();

	public String getLayoutPath(long layoutId);

	public Map<String, Lock> getLocks();

	public Map<?, ?> getNewPrimaryKeysMap(Class<?> classObj);

	public Map<?, ?> getNewPrimaryKeysMap(String className);

	public long getOldPlid();

	public Map<String, String[]> getParameterMap();

	public Map<String, List<KeyValuePair>> getPermissions();

	public long getPlid();

	public String getPortletPath(String portletId);

	public Set<String> getPrimaryKeys();

	public Map<String, List<RatingsEntry>> getRatingsEntries();

	public String getRootPath();

	public long getScopeGroupId();

	public String getScopeLayoutUuid();

	public long getSourceGroupId();

	public String getSourceLayoutPath(long layoutId);

	public String getSourcePortletPath(String portletId);

	public String getSourceRootPath();

	public Date getStartDate();

	public long getUserId(String userUuid) throws SystemException;

	public UserIdStrategy getUserIdStrategy() throws SystemException;

	public List<String> getZipEntries();

	public byte[] getZipEntryAsByteArray(String path);

	public InputStream getZipEntryAsInputStream(String path);

	public Object getZipEntryAsObject(String path);

	public String getZipEntryAsString(String path);

	public List<String> getZipFolderEntries();

	public List<String> getZipFolderEntries(String path);

	public ZipReader getZipReader();

	public ZipWriter getZipWriter();

	public boolean hasDateRange();

	public boolean hasNotUniquePerLayout(String dataKey);

	public boolean hasPrimaryKey(Class<?> classObj, String primaryKey);

	public void importComments(
			Class<?> classObj, long classPK, long newClassPK, long groupId)
		throws PortalException, SystemException;

	public void importLocks(Class<?> classObj, String key, String newKey)
		throws PortalException, SystemException;

	public void importPermissions(
			Class<?> classObj, long classPK, long newClassPK)
		throws PortalException, SystemException;

	public void importPermissions(
			String resourceObj, long resourcePK, long newResourcePK)
		throws PortalException, SystemException;

	public void importRatingsEntries(
			Class<?> classObj, long classPK, long newClassPK)
		throws PortalException, SystemException;

	public boolean isDataStrategyMirror();

	public boolean isPathNotProcessed(String path);

	public boolean isPrivateLayout();

	public boolean isWithinDateRange(Date modifiedDate);

	public void putNotUniquePerLayout(String dataKey);

	public void setClassLoader(ClassLoader classLoader);

	public void setGroupId(long groupId);

	public void setOldPlid(long oldPlid);

	public void setPlid(long plid);

	public void setPortetDataContextListener(
		PortletDataContextListener portletDataContextListener);

	public void setPrivateLayout(boolean privateLayout);

	public void setScopeGroupId(long scopeGroupId);

	public void setScopeLayoutUuid(String scopeLayoutUuid);

	public void setSourceGroupId(long sourceGroupId);

	public String toXML(Object object);

}