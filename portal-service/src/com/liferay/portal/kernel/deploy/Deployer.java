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

package com.liferay.portal.kernel.deploy;

import com.liferay.portal.kernel.plugin.PluginPackage;

import java.io.File;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public interface Deployer {

	public void copyDependencyXml(String fileName, String targetDir)
		throws Exception;

	public void copyDependencyXml(
			String fileName, String targetDir, Map<String, String> filterMap)
		throws Exception;

	public void copyDependencyXml(
			String fileName, String targetDir, Map<String, String> filterMap,
			boolean overwrite)
		throws Exception;

	public void copyJars(File srcFile, PluginPackage pluginPackage)
		throws Exception;

	public void copyProperties(File srcFile, PluginPackage pluginPackage)
		throws Exception;

	public void copyTlds(File srcFile, PluginPackage pluginPackage)
		throws Exception;

	public void copyXmls(
			File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception;

	public void processPluginPackageProperties(
			File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception;

	public PluginPackage readPluginPackage(File file);

	public void updateWebXml(
			File webXml, File srcFile, String displayName,
			PluginPackage pluginPackage)
		throws Exception;

}