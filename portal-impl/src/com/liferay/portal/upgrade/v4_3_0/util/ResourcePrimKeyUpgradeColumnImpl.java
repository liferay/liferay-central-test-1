/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v4_3_0.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.util.PortalUtil;

import java.util.Map;

/**
 * <a href="ResourcePrimKeyUpgradeColumnImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ResourcePrimKeyUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public ResourcePrimKeyUpgradeColumnImpl(
		UpgradeColumn nameColumn, ResourceCodeIdUpgradeColumnImpl codeIdColumn,
		ValueMapper groupIdMapper,
		Map<Long, ClassPKContainer> classPKContainers,
		ValueMapper layoutPlidMapper) {

		super("primKey");

		_nameColumn = nameColumn;
		_codeIdColumn = codeIdColumn;
		_groupIdMapper = groupIdMapper;
		_classPKContainers = classPKContainers;
		_layoutPlidMapper = layoutPlidMapper;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		String primKey = (String)oldValue;

		int scope = _codeIdColumn.getScope();

		if (scope == ResourceConstants.SCOPE_COMPANY) {
			return primKey;
		}
		else if (scope == ResourceConstants.SCOPE_GROUP) {
			return String.valueOf(_groupIdMapper.getNewValue(
				new Long(GetterUtil.getLong(primKey))));
		}
		else if (scope == ResourceConstants.SCOPE_INDIVIDUAL) {
			String name = (String)_nameColumn.getOldValue();

			if (name.startsWith("com.liferay.")) {
				primKey = getClassPKPrimKey(name, primKey);
			}
			else if ((primKey.indexOf("_LAYOUT_") > 0) &&
					 (primKey.startsWith("PUB.") ||
					  primKey.startsWith("PRI."))) {

				primKey = getLayoutPrimKey(primKey);
			}

			return primKey;
		}
		else {
			throw new UpgradeException("Scope " + scope + " is invalid");
		}
	}

	protected String getClassPKPrimKey(String name, String primKey)
		throws Exception {

		Long classNameId = new Long(PortalUtil.getClassNameId(name));

		ClassPKContainer classPKContainer = _classPKContainers.get(classNameId);

		if (classPKContainer != null) {
			ValueMapper valueMapper = classPKContainer.getValueMapper();

			if (valueMapper == null) {
				_log.error("Name " + name + " does not have a value mapper");
			}
			else {
				if (classPKContainer.isLong()) {
					primKey = String.valueOf(valueMapper.getNewValue(
						new Long(GetterUtil.getLong(primKey))));
				}
				else {
					primKey = String.valueOf(valueMapper.getNewValue(primKey));
				}
			}
		}
		else {
			_log.error("Name " + name + " is invalid");
		}

		return primKey;
	}

	protected String getLayoutPrimKey(String oldPrimKey) throws Exception {
		int x = oldPrimKey.indexOf(StringPool.PERIOD, 4);
		int y = oldPrimKey.indexOf(PortletConstants.LAYOUT_SEPARATOR);

		String oldOwnerId = oldPrimKey.substring(0, x);
		String layoutId = oldPrimKey.substring(x + 1, y);

		String oldPlidValue =
			"{layoutId=" + layoutId + ", ownerId=" + oldOwnerId + "}";

		Object newPlid = _layoutPlidMapper.getNewValue(oldPlidValue);

		String newPrimKey = newPlid + oldPrimKey.substring(y);

		return newPrimKey;
	}

	private static Log _log =
		LogFactoryUtil.getLog(ResourcePrimKeyUpgradeColumnImpl.class);

	private UpgradeColumn _nameColumn;
	private ResourceCodeIdUpgradeColumnImpl _codeIdColumn;
	private ValueMapper _groupIdMapper;
	private Map<Long, ClassPKContainer> _classPKContainers;
	private ValueMapper _layoutPlidMapper;

}