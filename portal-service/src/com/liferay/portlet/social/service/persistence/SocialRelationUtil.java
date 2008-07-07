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

package com.liferay.portlet.social.service.persistence;

/**
 * <a href="SocialRelationUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SocialRelationUtil {
	public static com.liferay.portlet.social.model.SocialRelation create(
		long relationId) {
		return getPersistence().create(relationId);
	}

	public static com.liferay.portlet.social.model.SocialRelation remove(
		long relationId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().remove(relationId);
	}

	public static com.liferay.portlet.social.model.SocialRelation remove(
		com.liferay.portlet.social.model.SocialRelation socialRelation)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(socialRelation);
	}

	/**
	 * @deprecated Use <code>update(SocialRelation socialRelation, boolean merge)</code>.
	 */
	public static com.liferay.portlet.social.model.SocialRelation update(
		com.liferay.portlet.social.model.SocialRelation socialRelation)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(socialRelation);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        socialRelation the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when socialRelation is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portlet.social.model.SocialRelation update(
		com.liferay.portlet.social.model.SocialRelation socialRelation,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(socialRelation, merge);
	}

	public static com.liferay.portlet.social.model.SocialRelation updateImpl(
		com.liferay.portlet.social.model.SocialRelation socialRelation,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(socialRelation, merge);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByPrimaryKey(
		long relationId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByPrimaryKey(relationId);
	}

	public static com.liferay.portlet.social.model.SocialRelation fetchByPrimaryKey(
		long relationId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(relationId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUuid(
		java.lang.String uuid) throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid, start, end, obc);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByUuid_First(uuid, obc);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByUuid_Last(uuid, obc);
	}

	public static com.liferay.portlet.social.model.SocialRelation[] findByUuid_PrevAndNext(
		long relationId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByUuid_PrevAndNext(relationId, uuid, obc);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.social.model.SocialRelation[] findByCompanyId_PrevAndNext(
		long relationId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(relationId, companyId, obc);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUserId1(
		long userId1) throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId1(userId1);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUserId1(
		long userId1, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId1(userId1, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUserId1(
		long userId1, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId1(userId1, start, end, obc);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByUserId1_First(
		long userId1, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByUserId1_First(userId1, obc);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByUserId1_Last(
		long userId1, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByUserId1_Last(userId1, obc);
	}

	public static com.liferay.portlet.social.model.SocialRelation[] findByUserId1_PrevAndNext(
		long relationId, long userId1,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByUserId1_PrevAndNext(relationId, userId1, obc);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUserId2(
		long userId2) throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId2(userId2);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUserId2(
		long userId2, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId2(userId2, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUserId2(
		long userId2, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId2(userId2, start, end, obc);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByUserId2_First(
		long userId2, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByUserId2_First(userId2, obc);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByUserId2_Last(
		long userId2, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByUserId2_Last(userId2, obc);
	}

	public static com.liferay.portlet.social.model.SocialRelation[] findByUserId2_PrevAndNext(
		long relationId, long userId2,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByUserId2_PrevAndNext(relationId, userId2, obc);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByType(
		int type) throws com.liferay.portal.SystemException {
		return getPersistence().findByType(type);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByType(
		int type, int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByType(type, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByType(
		int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByType(type, start, end, obc);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByType_First(
		int type, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByType_First(type, obc);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByType_Last(
		int type, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByType_Last(type, obc);
	}

	public static com.liferay.portlet.social.model.SocialRelation[] findByType_PrevAndNext(
		long relationId, int type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByType_PrevAndNext(relationId, type, obc);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByC_T(
		long companyId, int type) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_T(companyId, type);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByC_T(
		long companyId, int type, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_T(companyId, type, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByC_T(
		long companyId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_T(companyId, type, start, end, obc);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByC_T_First(
		long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByC_T_First(companyId, type, obc);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByC_T_Last(
		long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByC_T_Last(companyId, type, obc);
	}

	public static com.liferay.portlet.social.model.SocialRelation[] findByC_T_PrevAndNext(
		long relationId, long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByC_T_PrevAndNext(relationId, companyId, type, obc);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU1_T(
		long userId1, int type) throws com.liferay.portal.SystemException {
		return getPersistence().findByU1_T(userId1, type);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU1_T(
		long userId1, int type, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByU1_T(userId1, type, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU1_T(
		long userId1, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByU1_T(userId1, type, start, end, obc);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByU1_T_First(
		long userId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByU1_T_First(userId1, type, obc);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByU1_T_Last(
		long userId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByU1_T_Last(userId1, type, obc);
	}

	public static com.liferay.portlet.social.model.SocialRelation[] findByU1_T_PrevAndNext(
		long relationId, long userId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByU1_T_PrevAndNext(relationId, userId1, type, obc);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU2_T(
		long userId2, int type) throws com.liferay.portal.SystemException {
		return getPersistence().findByU2_T(userId2, type);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU2_T(
		long userId2, int type, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByU2_T(userId2, type, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU2_T(
		long userId2, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByU2_T(userId2, type, start, end, obc);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByU2_T_First(
		long userId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByU2_T_First(userId2, type, obc);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByU2_T_Last(
		long userId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByU2_T_Last(userId2, type, obc);
	}

	public static com.liferay.portlet.social.model.SocialRelation[] findByU2_T_PrevAndNext(
		long relationId, long userId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByU2_T_PrevAndNext(relationId, userId2, type, obc);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByU1_U2_T(
		long userId1, long userId2, int type)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByU1_U2_T(userId1, userId2, type);
	}

	public static com.liferay.portlet.social.model.SocialRelation fetchByU1_U2_T(
		long userId1, long userId2, int type)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByU1_U2_T(userId1, userId2, type);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByUserId1(long userId1)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUserId1(userId1);
	}

	public static void removeByUserId2(long userId2)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUserId2(userId2);
	}

	public static void removeByType(int type)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByType(type);
	}

	public static void removeByC_T(long companyId, int type)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_T(companyId, type);
	}

	public static void removeByU1_T(long userId1, int type)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByU1_T(userId1, type);
	}

	public static void removeByU2_T(long userId2, int type)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByU2_T(userId2, type);
	}

	public static void removeByU1_U2_T(long userId1, long userId2, int type)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		getPersistence().removeByU1_U2_T(userId1, userId2, type);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByUserId1(long userId1)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserId1(userId1);
	}

	public static int countByUserId2(long userId2)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserId2(userId2);
	}

	public static int countByType(int type)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByType(type);
	}

	public static int countByC_T(long companyId, int type)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_T(companyId, type);
	}

	public static int countByU1_T(long userId1, int type)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByU1_T(userId1, type);
	}

	public static int countByU2_T(long userId2, int type)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByU2_T(userId2, type);
	}

	public static int countByU1_U2_T(long userId1, long userId2, int type)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByU1_U2_T(userId1, userId2, type);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void registerListener(
		com.liferay.portal.model.ModelListener listener) {
		getPersistence().registerListener(listener);
	}

	public static void unregisterListener(
		com.liferay.portal.model.ModelListener listener) {
		getPersistence().unregisterListener(listener);
	}

	public static SocialRelationPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(SocialRelationPersistence persistence) {
		_persistence = persistence;
	}

	private static SocialRelationUtil _getUtil() {
		if (_util == null) {
			_util = (SocialRelationUtil)com.liferay.portal.kernel.bean.PortalBeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = SocialRelationUtil.class.getName();
	private static SocialRelationUtil _util;
	private SocialRelationPersistence _persistence;
}