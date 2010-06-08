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

package com.liferay.portlet.social.model.impl;

import com.liferay.ibm.icu.util.Calendar;
import com.liferay.ibm.icu.util.GregorianCalendar;
import com.liferay.portlet.social.model.SocialEquityUser;

import java.util.Date;

/**
 * <a href="SocialEquityUserImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Zsolt Berentey
 */
public class SocialEquityUserImpl
	extends SocialEquityUserModelImpl implements SocialEquityUser {

	public SocialEquityUserImpl() {
	}

	public double getContributionEquity() {
		return calculateEquity(getEquityDate(
			new Date()), getContributionK(), getContributionB());
	}

	public double getParticipationEquity() {
		return calculateEquity(getEquityDate(
			new Date()), getParticipationK(), getParticipationB());
	}

	protected double calculateEquity(int actionDate, double k, double b) {
		return k * actionDate + b;
	}

	protected int getEquityDate(Date date) {
		Calendar calendar = new GregorianCalendar(2010, Calendar.JANUARY, 1);

		return calendar.fieldDifference(date, Calendar.DATE);
	}

}