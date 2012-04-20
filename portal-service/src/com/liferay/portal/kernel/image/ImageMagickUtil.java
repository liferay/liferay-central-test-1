/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.image;

import java.util.List;
import java.util.Properties;

/**
 * The ImageMagick utility class.
 *
 * @author Alexander Chow
 */
public class ImageMagickUtil {

	/**
	 * Executes the <code>convert</code> command in ImageMagick.
	 *
	 * @param  arguments the command arguments being passed to <code>convert
	 *         <code>
	 * @param  fork whether to fork the process
	 * @throws Exception if an unexpected error occurred while executing command
	 * @see    <a href="http://www.imagemagick.org/script/convert.php">Convert
	 *         documentation</a>
	 */
	public static void convert(List<String> arguments, boolean fork)
		throws Exception {

		getImageMagick().convert(arguments, fork);
	}

	/**
	 * Returns the global search path configured for ImageMagick.
	 *
	 * @return the global search path
	 * @throws Exception if an unexpected error occurred
	 */
	public static String getGlobalSearchPath() throws Exception {
		return getImageMagick().getGlobalSearchPath();
	}

	public static ImageMagick getImageMagick() {
		return _imageMagick;
	}

	/**
	 * Returns the cache and resource usage limits configured for ImageMagick.
	 *
	 * @return the resource limits
	 * @throws Exception if an unexpected error occurred
	 */
	public static Properties getResourceLimitsProperties() throws Exception {
		return getImageMagick().getResourceLimitsProperties();
	}

	/**
	 * Executes the <code>identify</code> command in ImageMagick.
	 *
	 * @param  arguments the command arguments being passed to <code>identify
	 *         </code>
	 * @param  fork whether to fork the process
	 * @return the results of the <code>identify</code> call
	 * @throws Exception if an unexpected error occurred while executing command
	 * @see    <a href="http://www.imagemagick.org/script/identify.php">Identify
	 *         documentation</a>
	 */
	public static String[] identify(List<String> arguments, boolean fork)
		throws Exception {

		return getImageMagick().identify(arguments, fork);
	}

	/**
	 * Returns <code>true</code> if ImageMagick is enabled.
	 *
	 * @return <code>true</code> if ImageMagick is enabled; <code>false</code>
	 *         otherwise
	 */
	public static boolean isEnabled() {
		return getImageMagick().isEnabled();
	}

	/**
	 * Resets the global search path and resource limits for ImageMagick.
	 */
	public static void reset() {
		getImageMagick().reset();
	}

	public void setImageMagick(ImageMagick imageMagick) {
		_imageMagick = imageMagick;
	}

	private static ImageMagick _imageMagick;

}