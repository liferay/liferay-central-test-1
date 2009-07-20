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

package com.liferay.portlet.social.service;

public class SocialActivityInterpreterLocalServiceUtil {
	public static void addActivityInterpreter(
		com.liferay.portlet.social.model.SocialActivityInterpreter activityInterpreter) {
		getService().addActivityInterpreter(activityInterpreter);
	}

	public static void deleteActivityInterpreter(
		com.liferay.portlet.social.model.SocialActivityInterpreter activityInterpreter) {
		getService().deleteActivityInterpreter(activityInterpreter);
	}

	public static com.liferay.portlet.social.model.SocialActivityFeedEntry interpret(
		com.liferay.portlet.social.model.SocialActivity activity,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return getService().interpret(activity, themeDisplay);
	}

	public static SocialActivityInterpreterLocalService getService() {
		if (_service == null) {
			throw new RuntimeException(
				"SocialActivityInterpreterLocalService is not set");
		}

		return _service;
	}

	public void setService(SocialActivityInterpreterLocalService service) {
		_service = service;
	}

	private static SocialActivityInterpreterLocalService _service;
}