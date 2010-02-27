/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.shopping.model;


/**
 * <a href="ShoppingCoupon.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the ShoppingCoupon table in the
 * database.
 * </p>
 *
 * <p>
 * Customize {@link com.liferay.portlet.shopping.model.impl.ShoppingCouponImpl} and rerun the
 * ServiceBuilder to generate the new methods.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingCouponModel
 * @see       com.liferay.portlet.shopping.model.impl.ShoppingCouponImpl
 * @see       com.liferay.portlet.shopping.model.impl.ShoppingCouponModelImpl
 * @generated
 */
public interface ShoppingCoupon extends ShoppingCouponModel {
	public boolean hasValidDateRange();

	public boolean hasValidEndDate();

	public boolean hasValidStartDate();
}