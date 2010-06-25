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

package com.liferay.portal.kernel.cluster;

import java.io.Serializable;

/**
 * <a href="ClusterRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Tina Tian
 */
public interface ClusterRequest extends Serializable {

	public Object getPayload();

	public String getUuid();

	public boolean isMulticast();

	public boolean isSkipLocal();

	public void setMulticast(boolean multicast);

	public void setPayload(Object payload);

	public void setSkipLocal(boolean skipLocal);

	public void setUuid(String uuid);

}