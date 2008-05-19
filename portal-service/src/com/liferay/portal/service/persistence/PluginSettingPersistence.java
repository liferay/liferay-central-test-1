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

package com.liferay.portal.service.persistence;

/**
 * <a href="PluginSettingPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface PluginSettingPersistence {
	public com.liferay.portal.model.PluginSetting create(long pluginSettingId);

	public com.liferay.portal.model.PluginSetting remove(long pluginSettingId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchPluginSettingException;

	public com.liferay.portal.model.PluginSetting remove(
		com.liferay.portal.model.PluginSetting pluginSetting)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(PluginSetting pluginSetting, boolean merge)</code>.
	 */
	public com.liferay.portal.model.PluginSetting update(
		com.liferay.portal.model.PluginSetting pluginSetting)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        pluginSetting the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when pluginSetting is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public com.liferay.portal.model.PluginSetting update(
		com.liferay.portal.model.PluginSetting pluginSetting, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.PluginSetting updateImpl(
		com.liferay.portal.model.PluginSetting pluginSetting, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.PluginSetting findByPrimaryKey(
		long pluginSettingId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchPluginSettingException;

	public com.liferay.portal.model.PluginSetting fetchByPrimaryKey(
		long pluginSettingId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.PluginSetting> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.PluginSetting> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.PluginSetting> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.PluginSetting findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchPluginSettingException;

	public com.liferay.portal.model.PluginSetting findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchPluginSettingException;

	public com.liferay.portal.model.PluginSetting[] findByCompanyId_PrevAndNext(
		long pluginSettingId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchPluginSettingException;

	public com.liferay.portal.model.PluginSetting findByC_I_T(long companyId,
		java.lang.String pluginId, java.lang.String pluginType)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchPluginSettingException;

	public com.liferay.portal.model.PluginSetting fetchByC_I_T(long companyId,
		java.lang.String pluginId, java.lang.String pluginType)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.PluginSetting> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.PluginSetting> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.PluginSetting> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.PluginSetting> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.PluginSetting> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public void removeByC_I_T(long companyId, java.lang.String pluginId,
		java.lang.String pluginType)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchPluginSettingException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public int countByC_I_T(long companyId, java.lang.String pluginId,
		java.lang.String pluginType) throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}