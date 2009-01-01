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

package com.liferay.wsrp.model;

import com.liferay.portal.model.BaseModel;

/**
 * <a href="WSRPPortletModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the <code>WSRPPortlet</code>
 * table in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.wsrp.model.WSRPPortlet
 * @see com.liferay.wsrp.model.impl.WSRPPortletImpl
 * @see com.liferay.wsrp.model.impl.WSRPPortletModelImpl
 *
 */
public interface WSRPPortletModel extends BaseModel {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getPortletId();

	public void setPortletId(long portletId);

	public String getName();

	public void setName(String name);

	public String getChannelName();

	public void setChannelName(String channelName);

	public String getTitle();

	public void setTitle(String title);

	public String getShortTitle();

	public void setShortTitle(String shortTitle);

	public String getDisplayName();

	public void setDisplayName(String displayName);

	public String getKeywords();

	public void setKeywords(String keywords);

	public int getStatus();

	public void setStatus(int status);

	public String getProducerEntityId();

	public void setProducerEntityId(String producerEntityId);

	public String getConsumerId();

	public void setConsumerId(String consumerId);

	public String getPortletHandle();

	public void setPortletHandle(String portletHandle);

	public String getMimeTypes();

	public void setMimeTypes(String mimeTypes);

	public WSRPPortlet toEscapedModel();
}