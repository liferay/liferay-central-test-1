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

import com.liferay.portal.NoSuchImageException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.ImageImpl;
import com.liferay.portal.model.impl.ImageModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ImagePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ImagePersistence
 * @see       ImageUtil
 * @generated
 */
public class ImagePersistenceImpl extends BasePersistenceImpl
	implements ImagePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = ImageImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_SIZE = new FinderPath(ImageModelImpl.ENTITY_CACHE_ENABLED,
			ImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findBySize", new String[] { Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_SIZE = new FinderPath(ImageModelImpl.ENTITY_CACHE_ENABLED,
			ImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findBySize",
			new String[] {
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_SIZE = new FinderPath(ImageModelImpl.ENTITY_CACHE_ENABLED,
			ImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countBySize", new String[] { Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ImageModelImpl.ENTITY_CACHE_ENABLED,
			ImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ImageModelImpl.ENTITY_CACHE_ENABLED,
			ImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(Image image) {
		EntityCacheUtil.putResult(ImageModelImpl.ENTITY_CACHE_ENABLED,
			ImageImpl.class, image.getPrimaryKey(), image);
	}

	public void cacheResult(List<Image> images) {
		for (Image image : images) {
			if (EntityCacheUtil.getResult(ImageModelImpl.ENTITY_CACHE_ENABLED,
						ImageImpl.class, image.getPrimaryKey(), this) == null) {
				cacheResult(image);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(ImageImpl.class.getName());
		EntityCacheUtil.clearCache(ImageImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public Image create(long imageId) {
		Image image = new ImageImpl();

		image.setNew(true);
		image.setPrimaryKey(imageId);

		return image;
	}

	public Image remove(long imageId)
		throws NoSuchImageException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Image image = (Image)session.get(ImageImpl.class, new Long(imageId));

			if (image == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Image exists with the primary key " +
						imageId);
				}

				throw new NoSuchImageException(
					"No Image exists with the primary key " + imageId);
			}

			return remove(image);
		}
		catch (NoSuchImageException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Image remove(Image image) throws SystemException {
		for (ModelListener<Image> listener : listeners) {
			listener.onBeforeRemove(image);
		}

		image = removeImpl(image);

		for (ModelListener<Image> listener : listeners) {
			listener.onAfterRemove(image);
		}

		return image;
	}

	protected Image removeImpl(Image image) throws SystemException {
		image = toUnwrappedModel(image);

		Session session = null;

		try {
			session = openSession();

			if (image.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ImageImpl.class,
						image.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(image);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(ImageModelImpl.ENTITY_CACHE_ENABLED,
			ImageImpl.class, image.getPrimaryKey());

		return image;
	}

	/**
	 * @deprecated Use {@link #update(Image, boolean merge)}.
	 */
	public Image update(Image image) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(Image image) method. Use update(Image image, boolean merge) instead.");
		}

		return update(image, false);
	}

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
	public Image update(Image image, boolean merge) throws SystemException {
		boolean isNew = image.isNew();

		for (ModelListener<Image> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(image);
			}
			else {
				listener.onBeforeUpdate(image);
			}
		}

		image = updateImpl(image, merge);

		for (ModelListener<Image> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(image);
			}
			else {
				listener.onAfterUpdate(image);
			}
		}

		return image;
	}

	public Image updateImpl(com.liferay.portal.model.Image image, boolean merge)
		throws SystemException {
		image = toUnwrappedModel(image);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, image, merge);

			image.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ImageModelImpl.ENTITY_CACHE_ENABLED,
			ImageImpl.class, image.getPrimaryKey(), image);

		return image;
	}

	protected Image toUnwrappedModel(Image image) {
		if (image instanceof ImageImpl) {
			return image;
		}

		ImageImpl imageImpl = new ImageImpl();

		imageImpl.setNew(image.isNew());
		imageImpl.setPrimaryKey(image.getPrimaryKey());

		imageImpl.setImageId(image.getImageId());
		imageImpl.setModifiedDate(image.getModifiedDate());
		imageImpl.setText(image.getText());
		imageImpl.setType(image.getType());
		imageImpl.setHeight(image.getHeight());
		imageImpl.setWidth(image.getWidth());
		imageImpl.setSize(image.getSize());

		return imageImpl;
	}

	public Image findByPrimaryKey(long imageId)
		throws NoSuchImageException, SystemException {
		Image image = fetchByPrimaryKey(imageId);

		if (image == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No Image exists with the primary key " + imageId);
			}

			throw new NoSuchImageException(
				"No Image exists with the primary key " + imageId);
		}

		return image;
	}

	public Image fetchByPrimaryKey(long imageId) throws SystemException {
		Image image = (Image)EntityCacheUtil.getResult(ImageModelImpl.ENTITY_CACHE_ENABLED,
				ImageImpl.class, imageId, this);

		if (image == null) {
			Session session = null;

			try {
				session = openSession();

				image = (Image)session.get(ImageImpl.class, new Long(imageId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (image != null) {
					cacheResult(image);
				}

				closeSession(session);
			}
		}

		return image;
	}

	public List<Image> findBySize(int size) throws SystemException {
		Object[] finderArgs = new Object[] { new Integer(size) };

		List<Image> list = (List<Image>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_SIZE,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT image FROM Image image WHERE ");

				query.append("image.size < ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("image.imageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(size);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Image>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_SIZE, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Image> findBySize(int size, int start, int end)
		throws SystemException {
		return findBySize(size, start, end, null);
	}

	public List<Image> findBySize(int size, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Integer(size),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Image> list = (List<Image>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_SIZE,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT image FROM Image image WHERE ");

				query.append("image.size < ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("image.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append("ORDER BY ");

					query.append("image.imageId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(size);

				list = (List<Image>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Image>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_SIZE,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Image findBySize_First(int size, OrderByComparator obc)
		throws NoSuchImageException, SystemException {
		List<Image> list = findBySize(size, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Image exists with the key {");

			msg.append("size=" + size);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Image findBySize_Last(int size, OrderByComparator obc)
		throws NoSuchImageException, SystemException {
		int count = countBySize(size);

		List<Image> list = findBySize(size, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Image exists with the key {");

			msg.append("size=" + size);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Image[] findBySize_PrevAndNext(long imageId, int size,
		OrderByComparator obc) throws NoSuchImageException, SystemException {
		Image image = findByPrimaryKey(imageId);

		int count = countBySize(size);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT image FROM Image image WHERE ");

			query.append("image.size < ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("image.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			else {
				query.append("ORDER BY ");

				query.append("image.imageId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(size);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, image);

			Image[] array = new ImageImpl[3];

			array[0] = (Image)objArray[0];
			array[1] = (Image)objArray[1];
			array[2] = (Image)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.setLimit(start, end);

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Image> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Image> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<Image> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Image> list = (List<Image>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT image FROM Image image ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("image.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append("ORDER BY ");

					query.append("image.imageId ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<Image>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Image>)QueryUtil.list(q, getDialect(), start,
							end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Image>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeBySize(int size) throws SystemException {
		for (Image image : findBySize(size)) {
			remove(image);
		}
	}

	public void removeAll() throws SystemException {
		for (Image image : findAll()) {
			remove(image);
		}
	}

	public int countBySize(int size) throws SystemException {
		Object[] finderArgs = new Object[] { new Integer(size) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(image) ");
				query.append("FROM Image image WHERE ");

				query.append("image.size < ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(size);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(image) FROM Image image");

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.Image")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Image>> listenersList = new ArrayList<ModelListener<Image>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Image>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portal.service.persistence.AccountPersistence.impl")
	protected com.liferay.portal.service.persistence.AccountPersistence accountPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.AddressPersistence.impl")
	protected com.liferay.portal.service.persistence.AddressPersistence addressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.BrowserTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ClassNamePersistence.impl")
	protected com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence.impl")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ContactPersistence.impl")
	protected com.liferay.portal.service.persistence.ContactPersistence contactPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CountryPersistence.impl")
	protected com.liferay.portal.service.persistence.CountryPersistence countryPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.EmailAddressPersistence.impl")
	protected com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence.impl")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence.impl")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPrototypePersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPrototypePersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ListTypePersistence.impl")
	protected com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LockPersistence.impl")
	protected com.liferay.portal.service.persistence.LockPersistence lockPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.MembershipRequestPersistence.impl")
	protected com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrganizationPersistence.impl")
	protected com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupPermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgLaborPersistence.impl")
	protected com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyRelPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PhonePersistence.impl")
	protected com.liferay.portal.service.persistence.PhonePersistence phonePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PluginSettingPersistence.impl")
	protected com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletPersistence portletPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletItemPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RegionPersistence.impl")
	protected com.liferay.portal.service.persistence.RegionPersistence regionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ReleasePersistence.impl")
	protected com.liferay.portal.service.persistence.ReleasePersistence releasePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceActionPersistence.impl")
	protected com.liferay.portal.service.persistence.ResourceActionPersistence resourceActionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceCodePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RolePersistence.impl")
	protected com.liferay.portal.service.persistence.RolePersistence rolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ServiceComponentPersistence.impl")
	protected com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ShardPersistence.impl")
	protected com.liferay.portal.service.persistence.ShardPersistence shardPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.SubscriptionPersistence.impl")
	protected com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupPersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserIdMapperPersistence.impl")
	protected com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPathPersistence.impl")
	protected com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence.impl")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebsitePersistence.impl")
	protected com.liferay.portal.service.persistence.WebsitePersistence websitePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowLinkPersistence.impl")
	protected com.liferay.portal.service.persistence.WorkflowLinkPersistence workflowLinkPersistence;
	private static Log _log = LogFactoryUtil.getLog(ImagePersistenceImpl.class);
}