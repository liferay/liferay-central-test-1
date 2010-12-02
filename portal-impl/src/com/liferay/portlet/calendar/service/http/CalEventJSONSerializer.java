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

package com.liferay.portlet.calendar.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.calendar.model.CalEvent;

import java.util.Date;
import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class CalEventJSONSerializer {
	public static JSONObject toJSONObject(CalEvent model) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("uuid", model.getUuid());
		jsonObject.put("eventId", model.getEventId());
		jsonObject.put("groupId", model.getGroupId());
		jsonObject.put("companyId", model.getCompanyId());
		jsonObject.put("userId", model.getUserId());
		jsonObject.put("userName", model.getUserName());

		Date createDate = model.getCreateDate();

		String createDateJSON = StringPool.BLANK;

		if (createDate != null) {
			createDateJSON = String.valueOf(createDate.getTime());
		}

		jsonObject.put("createDate", createDateJSON);

		Date modifiedDate = model.getModifiedDate();

		String modifiedDateJSON = StringPool.BLANK;

		if (modifiedDate != null) {
			modifiedDateJSON = String.valueOf(modifiedDate.getTime());
		}

		jsonObject.put("modifiedDate", modifiedDateJSON);
		jsonObject.put("title", model.getTitle());
		jsonObject.put("description", model.getDescription());

		Date startDate = model.getStartDate();

		String startDateJSON = StringPool.BLANK;

		if (startDate != null) {
			startDateJSON = String.valueOf(startDate.getTime());
		}

		jsonObject.put("startDate", startDateJSON);

		Date endDate = model.getEndDate();

		String endDateJSON = StringPool.BLANK;

		if (endDate != null) {
			endDateJSON = String.valueOf(endDate.getTime());
		}

		jsonObject.put("endDate", endDateJSON);
		jsonObject.put("durationHour", model.getDurationHour());
		jsonObject.put("durationMinute", model.getDurationMinute());
		jsonObject.put("allDay", model.getAllDay());
		jsonObject.put("timeZoneSensitive", model.getTimeZoneSensitive());
		jsonObject.put("type", model.getType());
		jsonObject.put("repeating", model.getRepeating());
		jsonObject.put("recurrence", model.getRecurrence());
		jsonObject.put("remindBy", model.getRemindBy());
		jsonObject.put("firstReminder", model.getFirstReminder());
		jsonObject.put("secondReminder", model.getSecondReminder());

		return jsonObject;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.calendar.model.CalEvent[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (CalEvent model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.calendar.model.CalEvent[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (CalEvent[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portlet.calendar.model.CalEvent> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (CalEvent model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}