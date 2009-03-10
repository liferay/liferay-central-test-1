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

package com.liferay.portal.service.persistence;

/**
 * <a href="ShardPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface ShardPersistence extends BasePersistence {
	public com.liferay.portal.model.Shard create(long shardId);

	public com.liferay.portal.model.Shard remove(long shardId)
		throws com.liferay.portal.NoSuchShardException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Shard remove(
		com.liferay.portal.model.Shard shard)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(Shard shard, boolean merge)</code>.
	 */
	public com.liferay.portal.model.Shard update(
		com.liferay.portal.model.Shard shard)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        shard the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when shard is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public com.liferay.portal.model.Shard update(
		com.liferay.portal.model.Shard shard, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Shard updateImpl(
		com.liferay.portal.model.Shard shard, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Shard findByPrimaryKey(long shardId)
		throws com.liferay.portal.NoSuchShardException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Shard fetchByPrimaryKey(long shardId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Shard findByName(java.lang.String name)
		throws com.liferay.portal.NoSuchShardException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Shard fetchByName(java.lang.String name)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Shard> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Shard> findAll(int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Shard> findAll(int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByName(java.lang.String name)
		throws com.liferay.portal.NoSuchShardException,
			com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByName(java.lang.String name)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Company> getCompanies(
		long pk) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Company> getCompanies(
		long pk, int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Company> getCompanies(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public int getCompaniesSize(long pk)
		throws com.liferay.portal.SystemException;

	public boolean containsCompany(long pk, long companyPK)
		throws com.liferay.portal.SystemException;

	public boolean containsCompanies(long pk)
		throws com.liferay.portal.SystemException;

	public void addCompany(long pk, long companyPK)
		throws com.liferay.portal.SystemException;

	public void addCompany(long pk, com.liferay.portal.model.Company company)
		throws com.liferay.portal.SystemException;

	public void addCompanies(long pk, long[] companyPKs)
		throws com.liferay.portal.SystemException;

	public void addCompanies(long pk,
		java.util.List<com.liferay.portal.model.Company> companies)
		throws com.liferay.portal.SystemException;

	public void clearCompanies(long pk)
		throws com.liferay.portal.SystemException;

	public void removeCompany(long pk, long companyPK)
		throws com.liferay.portal.SystemException;

	public void removeCompany(long pk, com.liferay.portal.model.Company company)
		throws com.liferay.portal.SystemException;

	public void removeCompanies(long pk, long[] companyPKs)
		throws com.liferay.portal.SystemException;

	public void removeCompanies(long pk,
		java.util.List<com.liferay.portal.model.Company> companies)
		throws com.liferay.portal.SystemException;

	public void setCompanies(long pk, long[] companyPKs)
		throws com.liferay.portal.SystemException;

	public void setCompanies(long pk,
		java.util.List<com.liferay.portal.model.Company> companies)
		throws com.liferay.portal.SystemException;
}