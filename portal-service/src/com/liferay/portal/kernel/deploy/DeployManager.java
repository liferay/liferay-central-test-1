/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.deploy;

import java.io.File;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public interface DeployManager {

	public void deploy(File source) throws Exception;

	public void deploy(File source, String context) throws Exception;

	public String getDeployDir() throws Exception;

	public void redeploy(String context) throws Exception;

	public void undeploy(String context) throws Exception;

}