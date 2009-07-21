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

public class ResourceCodeUtil {
	public static void cacheResult(
		com.liferay.portal.model.ResourceCode resourceCode) {
		getPersistence().cacheResult(resourceCode);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.ResourceCode> resourceCodes) {
		getPersistence().cacheResult(resourceCodes);
	}

	public static void clearCache() {
		getPersistence().clearCache();
	}

	public static com.liferay.portal.model.ResourceCode create(long codeId) {
		return getPersistence().create(codeId);
	}

	public static com.liferay.portal.model.ResourceCode remove(long codeId)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(codeId);
	}

	public static com.liferay.portal.model.ResourceCode remove(
		com.liferay.portal.model.ResourceCode resourceCode)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(resourceCode);
	}

	public static com.liferay.portal.model.ResourceCode update(
		com.liferay.portal.model.ResourceCode resourceCode)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(resourceCode);
	}

	public static com.liferay.portal.model.ResourceCode update(
		com.liferay.portal.model.ResourceCode resourceCode, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(resourceCode, merge);
	}

	public static com.liferay.portal.model.ResourceCode updateImpl(
		com.liferay.portal.model.ResourceCode resourceCode, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(resourceCode, merge);
	}

	public static com.liferay.portal.model.ResourceCode findByPrimaryKey(
		long codeId)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(codeId);
	}

	public static com.liferay.portal.model.ResourceCode fetchByPrimaryKey(
		long codeId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(codeId);
	}

	public static java.util.List<com.liferay.portal.model.ResourceCode> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portal.model.ResourceCode> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.ResourceCode> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portal.model.ResourceCode findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.ResourceCode findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.ResourceCode[] findByCompanyId_PrevAndNext(
		long codeId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(codeId, companyId, obc);
	}

	public static java.util.List<com.liferay.portal.model.ResourceCode> findByName(
		java.lang.String name) throws com.liferay.portal.SystemException {
		return getPersistence().findByName(name);
	}

	public static java.util.List<com.liferay.portal.model.ResourceCode> findByName(
		java.lang.String name, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByName(name, start, end);
	}

	public static java.util.List<com.liferay.portal.model.ResourceCode> findByName(
		java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByName(name, start, end, obc);
	}

	public static com.liferay.portal.model.ResourceCode findByName_First(
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.SystemException {
		return getPersistence().findByName_First(name, obc);
	}

	public static com.liferay.portal.model.ResourceCode findByName_Last(
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.SystemException {
		return getPersistence().findByName_Last(name, obc);
	}

	public static com.liferay.portal.model.ResourceCode[] findByName_PrevAndNext(
		long codeId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.SystemException {
		return getPersistence().findByName_PrevAndNext(codeId, name, obc);
	}

	public static com.liferay.portal.model.ResourceCode findByC_N_S(
		long companyId, java.lang.String name, int scope)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.SystemException {
		return getPersistence().findByC_N_S(companyId, name, scope);
	}

	public static com.liferay.portal.model.ResourceCode fetchByC_N_S(
		long companyId, java.lang.String name, int scope)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_N_S(companyId, name, scope);
	}

	public static com.liferay.portal.model.ResourceCode fetchByC_N_S(
		long companyId, java.lang.String name, int scope,
		boolean retrieveFromCache) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByC_N_S(companyId, name, scope, retrieveFromCache);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portal.model.ResourceCode> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.ResourceCode> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.ResourceCode> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByName(java.lang.String name)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByName(name);
	}

	public static void removeByC_N_S(long companyId, java.lang.String name,
		int scope)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.SystemException {
		getPersistence().removeByC_N_S(companyId, name, scope);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByName(java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByName(name);
	}

	public static int countByC_N_S(long companyId, java.lang.String name,
		int scope) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_N_S(companyId, name, scope);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static ResourceCodePersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(ResourceCodePersistence persistence) {
		_persistence = persistence;
	}

	private static ResourceCodePersistence _persistence;
}