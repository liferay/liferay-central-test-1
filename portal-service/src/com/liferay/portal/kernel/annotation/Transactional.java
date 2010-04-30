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

package com.liferay.portal.kernel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
/**
 * <a href="Transactional.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public @interface Transactional {

	public Isolation isolation() default Isolation.DEFAULT;

	public Class<? extends Throwable>[] noRollbackFor() default {};

	public String[] noRollbackForClassName() default {};

	public Propagation propagation() default Propagation.REQUIRED;

	public boolean readOnly() default false;

	public Class<? extends Throwable>[] rollbackFor() default {};

	public String[] rollbackForClassName() default {};

	public int timeout() default TransactionDefinition.TIMEOUT_DEFAULT;

}