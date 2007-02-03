/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import com.liferay.portal.model.BaseModel;

/**
 * <a href="GroupModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface GroupModel extends BaseModel {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getGroupId();

	public void setGroupId(long groupId);

	public String getCompanyId();

	public void setCompanyId(String companyId);

	public String getClassName();

	public void setClassName(String className);

	public String getClassPK();

	public void setClassPK(String classPK);

	public long getParentGroupId();

	public void setParentGroupId(long parentGroupId);

	public String getName();

	public void setName(String name);

	public String getDescription();

	public void setDescription(String description);

	public String getType();

	public void setType(String type);

	public String getFriendlyURL();

	public void setFriendlyURL(String friendlyURL);
}