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
 * <a href="ImagePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ImagePersistenceImpl
 * @see       ImageUtil
 * @generated
 */
public interface ImagePersistence extends BasePersistence {
	public void cacheResult(com.liferay.portal.model.Image image);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.Image> images);

	public void clearCache();

	public com.liferay.portal.model.Image create(long imageId);

	public com.liferay.portal.model.Image remove(long imageId)
		throws com.liferay.portal.NoSuchImageException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Image remove(
		com.liferay.portal.model.Image image)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use {@link #update(Image, boolean merge)}.
	 */
	public com.liferay.portal.model.Image update(
		com.liferay.portal.model.Image image)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param  image the entity to add, update, or merge
	 * @param  merge boolean value for whether to merge the entity. The default
	 *         value is false. Setting merge to true is more expensive and
	 *         should only be true when image is transient. See
	 *         LEP-5473 for a detailed discussion of this method.
	 * @return the entity that was added, updated, or merged
	 */
	public com.liferay.portal.model.Image update(
		com.liferay.portal.model.Image image, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Image updateImpl(
		com.liferay.portal.model.Image image, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Image findByPrimaryKey(long imageId)
		throws com.liferay.portal.NoSuchImageException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Image fetchByPrimaryKey(long imageId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Image> findBySize(int size)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Image> findBySize(int size,
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Image> findBySize(int size,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Image findBySize_First(int size,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchImageException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Image findBySize_Last(int size,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchImageException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Image[] findBySize_PrevAndNext(
		long imageId, int size,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchImageException,
			com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Image> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Image> findAll(int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Image> findAll(int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeBySize(int size)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countBySize(int size) throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}