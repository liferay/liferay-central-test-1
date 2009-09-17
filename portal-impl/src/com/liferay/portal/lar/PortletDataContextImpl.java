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

package com.liferay.portal.lar;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.blogs.model.impl.BlogsEntryImpl;
import com.liferay.portlet.bookmarks.model.impl.BookmarksEntryImpl;
import com.liferay.portlet.bookmarks.model.impl.BookmarksFolderImpl;
import com.liferay.portlet.calendar.model.impl.CalEventImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileRankImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.imagegallery.model.impl.IGFolderImpl;
import com.liferay.portlet.imagegallery.model.impl.IGImageImpl;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.portlet.journal.model.impl.JournalFeedImpl;
import com.liferay.portlet.journal.model.impl.JournalStructureImpl;
import com.liferay.portlet.journal.model.impl.JournalTemplateImpl;
import com.liferay.portlet.messageboards.NoSuchDiscussionException;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBBanImpl;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;
import com.liferay.portlet.messageboards.model.impl.MBMessageFlagImpl;
import com.liferay.portlet.messageboards.model.impl.MBMessageImpl;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.polls.model.impl.PollsChoiceImpl;
import com.liferay.portlet.polls.model.impl.PollsQuestionImpl;
import com.liferay.portlet.polls.model.impl.PollsVoteImpl;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.model.impl.RatingsEntryImpl;
import com.liferay.portlet.ratings.service.RatingsEntryLocalServiceUtil;
import com.liferay.portlet.wiki.model.impl.WikiNodeImpl;
import com.liferay.portlet.wiki.model.impl.WikiPageImpl;

import com.thoughtworks.xstream.XStream;

import java.io.IOException;
import java.io.InputStream;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <a href="PortletDataContextImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * Holds context information that is used during exporting and importing portlet
 * data.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Aug�
 * @author Bruno Farache
 * @author Alex Chow
 */
public class PortletDataContextImpl implements PortletDataContext {

	public PortletDataContextImpl(
			long companyId, long groupId, Map<String, String[]> parameterMap,
			Set<String> primaryKeys, Date startDate, Date endDate,
			ZipWriter zipWriter)
		throws PortletDataException {

		validateDateRange(startDate, endDate);

		_companyId = companyId;
		_groupId = groupId;
		_scopeGroupId = groupId;
		_parameterMap = parameterMap;
		_primaryKeys = primaryKeys;
		_dataStrategy =  null;
		_userIdStrategy = null;
		_startDate = startDate;
		_endDate = endDate;
		_zipReader = null;
		_zipWriter = zipWriter;

		initXStream();
	}

	public PortletDataContextImpl(
		long companyId, long groupId, Map<String, String[]> parameterMap,
		Set<String> primaryKeys, UserIdStrategy userIdStrategy,
		ZipReader zipReader) {

		_companyId = companyId;
		_groupId = groupId;
		_scopeGroupId = groupId;
		_parameterMap = parameterMap;
		_primaryKeys = primaryKeys;
		_dataStrategy =  MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.DATA_STRATEGY,
			PortletDataHandlerKeys.DATA_STRATEGY_MIRROR);
		_userIdStrategy = userIdStrategy;
		_zipReader = zipReader;
		_zipWriter = null;

		initXStream();
	}

	public void addAssetCategories(Class<?> classObj, long classPK)
		throws PortalException, SystemException {

		AssetEntry assetEntry = null;

		try {
			assetEntry = AssetEntryLocalServiceUtil.getEntry(
				classObj.getName(), classPK);
		}
		catch (NoSuchEntryException nsee) {

			// LEP-4979

			return;
		}

		List<AssetCategory> assetCategories = assetEntry.getCategories();

		if (assetCategories.isEmpty()) {
			return;
		}

		_assetCategoryUuidsMap.put(
			getPrimaryKeyString(classObj, classPK),
			StringUtil.split(ListUtil.toString(assetCategories, "uuid")));
		_assetCategoryIdsMap.put(
			getPrimaryKeyString(classObj, classPK),
			StringUtil.split(
				ListUtil.toString(assetCategories, "categoryId"), 0L));
	}

	public void addAssetCategories(
		String className, long classPK, long[] assetCategoryIds) {

		_assetCategoryIdsMap.put(
			getPrimaryKeyString(className, classPK), assetCategoryIds);
	}

	public void addAssetTags(Class<?> classObj, long classPK)
		throws PortalException, SystemException {

		AssetEntry assetEntry = null;

		try {
			assetEntry = AssetEntryLocalServiceUtil.getEntry(
				classObj.getName(), classPK);
		}
		catch (NoSuchEntryException nsee) {

			// LEP-4979

			return;
		}

		List<AssetTag> assetTags = assetEntry.getTags();

		if (assetTags.size() == 0) {
			return;
		}

		_assetTagNamesMap.put(
			getPrimaryKeyString(classObj, classPK),
			StringUtil.split(ListUtil.toString(assetTags, "name")));
	}

	public void addAssetTags(
		String className, long classPK, String[] assetTagNames) {

		_assetTagNamesMap.put(
			getPrimaryKeyString(className, classPK), assetTagNames);
	}

	public void addComments(Class<?> classObj, long classPK)
		throws SystemException {

		List<MBMessage> messages = MBMessageLocalServiceUtil.getMessages(
			classObj.getName(), classPK, StatusConstants.ANY);

		if (messages.size() == 0) {
			return;
		}

		Iterator<MBMessage> itr = messages.iterator();

		while (itr.hasNext()) {
			MBMessage message = itr.next();

			message.setUserUuid(message.getUserUuid());
		}

		_commentsMap.put(getPrimaryKeyString(classObj, classPK), messages);
	}

	public void addComments(
		String className, long classPK, List<MBMessage> messages) {

		_commentsMap.put(getPrimaryKeyString(className, classPK), messages);
	}

	public boolean addPrimaryKey(Class<?> classObj, String primaryKey) {
		boolean value = hasPrimaryKey(classObj, primaryKey);

		if (!value) {
			_primaryKeys.add(getPrimaryKeyString(classObj, primaryKey));
		}

		return value;
	}

	public void addRatingsEntries(Class<?> classObj, long classPK)
		throws SystemException {

		List<RatingsEntry> ratingsEntries =
			RatingsEntryLocalServiceUtil.getEntries(
				classObj.getName(), classPK);

		if (ratingsEntries.size() == 0) {
			return;
		}

		Iterator<RatingsEntry> itr = ratingsEntries.iterator();

		while (itr.hasNext()) {
			RatingsEntry entry = itr.next();

			entry.setUserUuid(entry.getUserUuid());
		}

		_ratingsEntriesMap.put(
			getPrimaryKeyString(classObj, classPK), ratingsEntries);
	}

	public void addRatingsEntries(
		String className, long classPK, List<RatingsEntry> ratingsEntries) {

		_ratingsEntriesMap.put(
			getPrimaryKeyString(className, classPK), ratingsEntries);
	}

	public void addZipEntry(String path, byte[] bytes) throws SystemException {
		try {
			getZipWriter().addEntry(path, bytes);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void addZipEntry(String path, InputStream is)
		throws SystemException {

		try {
			getZipWriter().addEntry(path, is);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void addZipEntry(String path, Object object) throws SystemException {
		addZipEntry(path, toXML(object));
	}

	public void addZipEntry(String path, String s) throws SystemException {
		try {
			getZipWriter().addEntry(path, s);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void addZipEntry(String path, StringBuilder sb)
		throws SystemException {

		try {
			getZipWriter().addEntry(path, sb);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public Object fromXML(byte[] bytes) {
		return _xStream.fromXML(new String(bytes));
	}

	public Object fromXML(String xml) {
		return _xStream.fromXML(xml);
	}

	public long[] getAssetCategoryIds(Class<?> classObj, long classPK) {
		return _assetCategoryIdsMap.get(
			getPrimaryKeyString(classObj, classPK));
	}

	public Map<String, long[]> getAssetCategoryIdsMap() {
		return _assetCategoryIdsMap;
	}

	public Map<String, String[]> getAssetCategoryUuidsMap() {
		return _assetCategoryUuidsMap;
	}

	public String[] getAssetTagNames(Class<?> classObj, long classPK) {
		return _assetTagNamesMap.get(getPrimaryKeyString(classObj, classPK));
	}

	public String[] getAssetTagNames(String className, long classPK) {
		return _assetTagNamesMap.get(getPrimaryKeyString(className, classPK));
	}

	public Map<String, String[]> getAssetTagNamesMap() {
		return _assetTagNamesMap;
	}

	public boolean getBooleanParameter(String namespace, String name) {
		boolean defaultValue = MapUtil.getBoolean(
			getParameterMap(),
			PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT, true);

		return MapUtil.getBoolean(
			getParameterMap(),
			PortletDataHandlerControl.getNamespacedControlName(namespace, name),
			defaultValue);
	}

	public ClassLoader getClassLoader() {
		return _xStream.getClassLoader();
	}

	public Map<String, List<MBMessage>> getComments() {
		return _commentsMap;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public String getDataStrategy() {
		return _dataStrategy;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public long getGroupId() {
		return _groupId;
	}

	public String getLayoutPath(long layoutId) {
		return getRootPath() + ROOT_PATH_LAYOUTS + layoutId;
	}

	public Map<?, ?> getNewPrimaryKeysMap(Class<?> classObj) {
		Map<?, ?> map = _newPrimaryKeysMaps.get(classObj.getName());

		if (map == null) {
			map = new HashMap<Object, Object>();

			_newPrimaryKeysMaps.put(classObj.getName(), map);
		}

		return map;
	}

	public long getOldPlid() {
		return _oldPlid;
	}

	public Map<String, String[]> getParameterMap() {
		return _parameterMap;
	}

	public long getPlid() {
		return _plid;
	}

	public String getPortletPath(String portletId) {
		return getRootPath() + ROOT_PATH_PORTLETS + portletId;
	}

	public Set<String> getPrimaryKeys() {
		return _primaryKeys;
	}

	public Map<String, List<RatingsEntry>> getRatingsEntries() {
		return _ratingsEntriesMap;
	}

	public String getRootPath() {
		return ROOT_PATH_GROUPS + getScopeGroupId();
	}

	public long getScopeGroupId() {
		return _scopeGroupId;
	}

	public long getScopeLayoutId() {
		return _scopeLayoutId;
	}

	public long getSourceGroupId() {
		return _sourceGroupId;
	}

	public String getSourceLayoutPath(long layoutId) {
		return getSourceRootPath() + ROOT_PATH_LAYOUTS + layoutId;
	}

	public String getSourcePortletPath(String portletId) {
		return getSourceRootPath() + ROOT_PATH_PORTLETS + portletId;
	}

	public String getSourceRootPath() {
		return ROOT_PATH_GROUPS + getSourceGroupId();
	}

	public Date getStartDate() {
		return _startDate;
	}

	public long getUserId(String userUuid) throws SystemException {
		return _userIdStrategy.getUserId(userUuid);
	}

	public UserIdStrategy getUserIdStrategy() {
		return _userIdStrategy;
	}

	public Map<String, byte[]> getZipEntries() {
		return getZipReader().getEntries();
	}

	public byte[] getZipEntryAsByteArray(String path) {
		return getZipReader().getEntryAsByteArray(path);
	}

	public Object getZipEntryAsObject(String path) {
		return fromXML(getZipEntryAsString(path));
	}

	public String getZipEntryAsString(String path) {
		return getZipReader().getEntryAsString(path);
	}

	public Map<String, List<ObjectValuePair<String, byte[]>>>
		getZipFolderEntries() {

		return getZipReader().getFolderEntries();
	}

	public List<ObjectValuePair<String, byte[]>> getZipFolderEntries(
		String path) {

		if (Validator.isNull(path)) {
			return null;
		}

		List<ObjectValuePair<String, byte[]>> folderEntries =
			getZipReader().getFolderEntries(path);

		if ((folderEntries == null) && path.startsWith(StringPool.SLASH)) {
			folderEntries = getZipReader().getFolderEntries(path.substring(1));
		}

		return folderEntries;
	}

	public ZipReader getZipReader() {
		return _zipReader;
	}

	public ZipWriter getZipWriter() {
		return _zipWriter;
	}

	public boolean hasDateRange() {
		if (_startDate != null) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean hasNotUniquePerLayout(String dataKey) {
		return _notUniquePerLayout.contains(dataKey);
	}
	public boolean hasPrimaryKey(Class<?> classObj, String primaryKey) {
		return _primaryKeys.contains(getPrimaryKeyString(classObj, primaryKey));
	}
	public void importComments(
			Class<?> classObj, long classPK, long newClassPK, long groupId)
		throws PortalException, SystemException {

		Map<Long, Long> messagePKs = new HashMap<Long, Long>();
		Map<Long, Long> threadPKs = new HashMap<Long, Long>();

		List<MBMessage> messages = _commentsMap.get(
			getPrimaryKeyString(classObj, classPK));

		if (messages == null) {
			return;
		}

		MBDiscussion discussion = null;

		try {
			discussion = MBDiscussionLocalServiceUtil.getDiscussion(
				classObj.getName(), newClassPK);
		}
		catch (NoSuchDiscussionException nsde) {
		}

		for (MBMessage message : messages) {
			long userId = getUserId(message.getUserUuid());
			long parentMessageId = MapUtil.getLong(
				messagePKs, message.getParentMessageId(),
				message.getParentMessageId());
			long threadId = MapUtil.getLong(
				threadPKs, message.getThreadId(), message.getThreadId());

			if ((message.getParentMessageId() ==
					MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) &&
				(discussion != null)) {

				MBThread thread = MBThreadLocalServiceUtil.getThread(
					discussion.getThreadId());

				long rootMessageId = thread.getRootMessageId();

				messagePKs.put(message.getMessageId(), rootMessageId);
				threadPKs.put(message.getThreadId(), thread.getThreadId());
			}
			else {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setScopeGroupId(groupId);

				MBMessage newMessage =
					MBMessageLocalServiceUtil.addDiscussionMessage(
						userId, message.getUserName(), classObj.getName(),
						newClassPK, threadId, parentMessageId,
						message.getSubject(), message.getBody(),
						StatusConstants.APPROVED, serviceContext);

				messagePKs.put(
					message.getMessageId(), newMessage.getMessageId());
				threadPKs.put(message.getThreadId(), newMessage.getThreadId());
			}
		}
	}
	public void importRatingsEntries(
			Class<?> classObj, long classPK, long newClassPK)
		throws PortalException, SystemException {

		List<RatingsEntry> ratingsEntries = _ratingsEntriesMap.get(
			getPrimaryKeyString(classObj, classPK));

		if (ratingsEntries == null) {
			return;
		}

		for (RatingsEntry ratingsEntry : ratingsEntries) {
			long userId = getUserId(ratingsEntry.getUserUuid());

			RatingsEntryLocalServiceUtil.updateEntry(
				userId, classObj.getName(), ((Long)newClassPK).longValue(),
				ratingsEntry.getScore());
		}
	}
	public boolean isPathNotProcessed(String path) {
		return !addPrimaryKey(String.class, path);
	}
	public boolean isPrivateLayout() {
		return _privateLayout;
	}
	public boolean isWithinDateRange(Date modifiedDate) {
		if (!hasDateRange()) {
			return true;
		}
		else if ((_startDate.compareTo(modifiedDate) <= 0) &&
				 (_endDate.after(modifiedDate))) {

			return true;
		}
		else {
			return false;
		}
	}
	public void putNotUniquePerLayout(String dataKey) {
		_notUniquePerLayout.add(dataKey);
	}
	public void setClassLoader(ClassLoader classLoader) {
		_xStream.setClassLoader(classLoader);
	}
	public void setGroupId(long groupId) {
		_groupId = groupId;
	}
	public void setOldPlid(long oldPlid) {
		_oldPlid = oldPlid;
	}
	public void setPlid(long plid) {
		_plid = plid;
	}
	public void setPrivateLayout(boolean privateLayout) {
		_privateLayout = privateLayout;
	}
	public void setScopeGroupId(long scopeGroupId) {
		_scopeGroupId = scopeGroupId;
	}
	public void setScopeLayoutId(long scopeLayoutId) {
		_scopeLayoutId = scopeLayoutId;
	}
	public void setSourceGroupId(long sourceGroupId) {
		_sourceGroupId = sourceGroupId;
	}
	public String toXML(Object object) {
		return _xStream.toXML(object);
	}
	protected String getPrimaryKeyString(Class<?> classObj, long classPK) {
		return getPrimaryKeyString(classObj.getName(), String.valueOf(classPK));
	}
	protected String getPrimaryKeyString(Class<?> classObj, String primaryKey) {
		return getPrimaryKeyString(classObj.getName(), primaryKey);
	}
	protected String getPrimaryKeyString(String className, long classPK) {
		return getPrimaryKeyString(className, String.valueOf(classPK));
	}
	protected String getPrimaryKeyString(String className, String primaryKey) {
		StringBuilder sb = new StringBuilder();

		sb.append(className);
		sb.append(StringPool.POUND);
		sb.append(primaryKey);

		return sb.toString();
	}
	protected void initXStream() {
		_xStream = new XStream();

		_xStream.alias("BlogsEntry", BlogsEntryImpl.class);
		_xStream.alias("BookmarksFolder", BookmarksFolderImpl.class);
		_xStream.alias("BookmarksEntry", BookmarksEntryImpl.class);
		_xStream.alias("CalEvent", CalEventImpl.class);
		_xStream.alias("DLFolder", DLFolderImpl.class);
		_xStream.alias("DLFileEntry", DLFileEntryImpl.class);
		_xStream.alias("DLFileShortcut", DLFileShortcutImpl.class);
		_xStream.alias("DLFileRank", DLFileRankImpl.class);
		_xStream.alias("IGFolder", IGFolderImpl.class);
		_xStream.alias("IGImage", IGImageImpl.class);
		_xStream.alias("JournalArticle", JournalArticleImpl.class);
		_xStream.alias("JournalFeed", JournalFeedImpl.class);
		_xStream.alias("JournalStructure", JournalStructureImpl.class);
		_xStream.alias("JournalTemplate", JournalTemplateImpl.class);
		_xStream.alias("MBCategory", MBCategoryImpl.class);
		_xStream.alias("MBMessage", MBMessageImpl.class);
		_xStream.alias("MBMessageFlag", MBMessageFlagImpl.class);
		_xStream.alias("MBBan", MBBanImpl.class);
		_xStream.alias("PollsQuestion", PollsQuestionImpl.class);
		_xStream.alias("PollsChoice", PollsChoiceImpl.class);
		_xStream.alias("PollsVote", PollsVoteImpl.class);
		_xStream.alias("RatingsEntry", RatingsEntryImpl.class);
		_xStream.alias("WikiNode", WikiNodeImpl.class);
		_xStream.alias("WikiPage", WikiPageImpl.class);
	}
	protected void validateDateRange(Date startDate, Date endDate)
		throws PortletDataException {

		if ((startDate == null) ^ (endDate == null)) {
			throw new PortletDataException(
				"Both start and end dates must have valid values or be null");
		}

		if (startDate != null) {
			if (startDate.after(endDate) || startDate.equals(endDate)) {
				throw new PortletDataException(
					"The start date cannot be after the end date");
			}

			Date now = new Date();

			if (startDate.after(now) || endDate.after(now)) {
				throw new PortletDataException(
					"Dates must not be in the future");
			}
		}
	}

	private Map<String, long[]> _assetCategoryIdsMap =
		new HashMap<String, long[]>();
	private Map<String, String[]> _assetCategoryUuidsMap =
		new HashMap<String, String[]>();
	private Map<String, String[]> _assetTagNamesMap =
		new HashMap<String, String[]>();
	private Map<String, List<MBMessage>> _commentsMap =
		new HashMap<String, List<MBMessage>>();
	private long _companyId;
	private String _dataStrategy;
	private Date _endDate;
	private long _groupId;
	private Map<String, Map<?, ?>> _newPrimaryKeysMaps =
		new HashMap<String, Map<?, ?>>();
	private Set<String> _notUniquePerLayout = new HashSet<String>();
	private long _oldPlid;
	private Map<String, String[]> _parameterMap;
	private long _plid;
	private Set<String> _primaryKeys;
	private boolean _privateLayout;
	private Map<String, List<RatingsEntry>> _ratingsEntriesMap =
		new HashMap<String, List<RatingsEntry>>();
	private long _scopeGroupId;
	private long _scopeLayoutId;
	private long _sourceGroupId;
	private Date _startDate;
	private UserIdStrategy _userIdStrategy;
	private XStream _xStream;
	private ZipReader _zipReader;
	private ZipWriter _zipWriter;

}