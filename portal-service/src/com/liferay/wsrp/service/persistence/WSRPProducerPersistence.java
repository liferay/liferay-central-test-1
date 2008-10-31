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

package com.liferay.wsrp.service.persistence;

import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;

/**
 * <a href="WSRPProducerPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
@Transactional
public interface WSRPProducerPersistence {
	public com.liferay.wsrp.model.WSRPProducer create(long producerId);

	public com.liferay.wsrp.model.WSRPProducer remove(long producerId)
		throws com.liferay.portal.SystemException,
			com.liferay.wsrp.NoSuchProducerException;

	public com.liferay.wsrp.model.WSRPProducer remove(
		com.liferay.wsrp.model.WSRPProducer wsrpProducer)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(WSRPProducer wsrpProducer, boolean merge)</code>.
	 */
	public com.liferay.wsrp.model.WSRPProducer update(
		com.liferay.wsrp.model.WSRPProducer wsrpProducer)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        wsrpProducer the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when wsrpProducer is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public com.liferay.wsrp.model.WSRPProducer update(
		com.liferay.wsrp.model.WSRPProducer wsrpProducer, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.wsrp.model.WSRPProducer updateImpl(
		com.liferay.wsrp.model.WSRPProducer wsrpProducer, boolean merge)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.wsrp.model.WSRPProducer findByPrimaryKey(long producerId)
		throws com.liferay.portal.SystemException,
			com.liferay.wsrp.NoSuchProducerException;

	public com.liferay.wsrp.model.WSRPProducer fetchByPrimaryKey(
		long producerId) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.wsrp.model.WSRPProducer findByInstanceName(
		java.lang.String instanceName)
		throws com.liferay.portal.SystemException,
			com.liferay.wsrp.NoSuchProducerException;

	public com.liferay.wsrp.model.WSRPProducer fetchByInstanceName(
		java.lang.String instanceName)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.wsrp.model.WSRPProducer> findByP_N(
		java.lang.String portalId, java.lang.String namespace)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.wsrp.model.WSRPProducer> findByP_N(
		java.lang.String portalId, java.lang.String namespace, int start,
		int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.wsrp.model.WSRPProducer> findByP_N(
		java.lang.String portalId, java.lang.String namespace, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.wsrp.model.WSRPProducer findByP_N_First(
		java.lang.String portalId, java.lang.String namespace,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.wsrp.NoSuchProducerException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.wsrp.model.WSRPProducer findByP_N_Last(
		java.lang.String portalId, java.lang.String namespace,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.wsrp.NoSuchProducerException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.wsrp.model.WSRPProducer[] findByP_N_PrevAndNext(
		long producerId, java.lang.String portalId, java.lang.String namespace,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.wsrp.NoSuchProducerException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.wsrp.model.WSRPProducer> findAll()
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.wsrp.model.WSRPProducer> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.wsrp.model.WSRPProducer> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByInstanceName(java.lang.String instanceName)
		throws com.liferay.portal.SystemException,
			com.liferay.wsrp.NoSuchProducerException;

	public void removeByP_N(java.lang.String portalId,
		java.lang.String namespace) throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int countByInstanceName(java.lang.String instanceName)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int countByP_N(java.lang.String portalId, java.lang.String namespace)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int countAll() throws com.liferay.portal.SystemException;

	public void registerListener(
		com.liferay.portal.model.ModelListener listener);

	public void unregisterListener(
		com.liferay.portal.model.ModelListener listener);
}