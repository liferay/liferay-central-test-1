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

package com.liferay.portal.search.lucene.messaging;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.messaging.BaseDestinationEventListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.search.BooleanQueryFactory;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.TermQueryFactory;
import com.liferay.portal.kernel.search.TermQueryFactoryUtil;
import com.liferay.portal.kernel.search.messaging.BaseSearchEngineMessageListener;
import com.liferay.portal.kernel.search.messaging.SearchReaderMessageListener;
import com.liferay.portal.kernel.search.messaging.SearchWriterMessageListener;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.search.lucene.LuceneSearchEngineImpl;
import com.liferay.portal.util.PropsUtil;

/**
 * <a href="SearchEngineDestinationEventListener.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Michael C. Han
 */
public class SearchEngineDestinationEventListener
	extends BaseDestinationEventListener {

	public void messageListenerRegistered(
		String destinationName, MessageListener messageListener) {

		if (!isProceed(destinationName, messageListener)) {
			return;
		}

		MessageBusUtil.unregisterMessageListener(
			DestinationNames.SEARCH_READER, _searchReaderMessageListener);
		MessageBusUtil.unregisterMessageListener(
			DestinationNames.SEARCH_WRITER, _searchWriterMessageListener);

		BaseSearchEngineMessageListener baseSearchEngineMessageListener =
			(BaseSearchEngineMessageListener)messageListener;

		if (!baseSearchEngineMessageListener.getSearchEngineName().contains(
				LuceneSearchEngineImpl.NAME)) {

			setBooleanQueryFactory(
				new com.liferay.portal.search.generic.
					BooleanQueryFactoryImpl());
			setTermQueryFactory(
				new com.liferay.portal.search.generic.TermQueryFactoryImpl());
			
			_indexWriteEventMessageListener.setActive(false);
		}
	}

	public void messageListenerUnregistered(
		String destinationName, MessageListener messageListener) {

		if (!isProceed(destinationName, messageListener)) {
			return;
		}

		MessageBusUtil.registerMessageListener(
			DestinationNames.SEARCH_READER, _searchReaderMessageListener);
		MessageBusUtil.registerMessageListener(
			DestinationNames.SEARCH_WRITER, _searchWriterMessageListener);

		BaseSearchEngineMessageListener baseSearchEngineMessageListener =
			(BaseSearchEngineMessageListener)messageListener;

		if (!baseSearchEngineMessageListener.getSearchEngineName().contains(
				LuceneSearchEngineImpl.NAME)) {

			setBooleanQueryFactory(
				new com.liferay.portal.search.lucene.BooleanQueryFactoryImpl());
			setTermQueryFactory(
				new com.liferay.portal.search.lucene.TermQueryFactoryImpl());

			_indexWriteEventMessageListener.setActive(_replicateWriteEvent);
		}
	}

	public void setIndexWriteEventMessageListener(
		IndexWriteEventReplicationMessageListener indexWriteEventMessageListener) {
		_indexWriteEventMessageListener = indexWriteEventMessageListener;
	}

	public void setSearchReaderMessageListener(
		SearchReaderMessageListener searchReaderMessageListener) {

		_searchReaderMessageListener = searchReaderMessageListener;
	}

	public void setSearchWriterMessageListener(
		SearchWriterMessageListener searchWriterMessageListener) {

		_searchWriterMessageListener = searchWriterMessageListener;
	}

	protected boolean isProceed(
		String destinationName, MessageListener messageListener) {

		if ((!destinationName.equals(DestinationNames.SEARCH_READER)) ||
			(messageListener == _searchReaderMessageListener) ||
			!(messageListener instanceof SearchReaderMessageListener)) {

			return false;
		}
		else {
			return true;
		}
	}

	protected void setBooleanQueryFactory(
		BooleanQueryFactory booleanQueryFactory) {

		BooleanQueryFactoryUtil booleanQueryFactoryUtil =
			(BooleanQueryFactoryUtil)PortalBeanLocatorUtil.locate(
				BooleanQueryFactoryUtil.class.getName());

		booleanQueryFactoryUtil.setBooleanQueryFactory(booleanQueryFactory);
	}

	protected void setTermQueryFactory(TermQueryFactory termQueryFactory) {
		TermQueryFactoryUtil termQueryFactoryUtil =
			(TermQueryFactoryUtil)PortalBeanLocatorUtil.locate(
				TermQueryFactoryUtil.class.getName());

		termQueryFactoryUtil.setTermQueryFactory(termQueryFactory);
	}

	private IndexWriteEventReplicationMessageListener _indexWriteEventMessageListener;
	private boolean _replicateWriteEvent = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.INDEX_REPLICATE_LUCENE_WRITE_MESSAGE), false);
	private SearchReaderMessageListener _searchReaderMessageListener;
	private SearchWriterMessageListener _searchWriterMessageListener;

}