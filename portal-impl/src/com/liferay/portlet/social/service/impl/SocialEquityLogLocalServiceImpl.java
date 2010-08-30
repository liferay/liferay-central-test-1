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

package com.liferay.portlet.social.service.impl;

import com.liferay.ibm.icu.util.Calendar;
import com.liferay.ibm.icu.util.GregorianCalendar;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.increment.BufferedIncrement;
import com.liferay.portal.kernel.increment.SocialEquityIncrement;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.social.NoSuchEquityAssetEntryException;
import com.liferay.portlet.social.model.SocialEquityAssetEntry;
import com.liferay.portlet.social.model.SocialEquityLog;
import com.liferay.portlet.social.model.SocialEquitySetting;
import com.liferay.portlet.social.model.SocialEquitySettingConstants;
import com.liferay.portlet.social.model.SocialEquityValue;
import com.liferay.portlet.social.service.base.SocialEquityLogLocalServiceBaseImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

/**
 * @author Zsolt Berentey
 * @author Brian Wing Shun Chan
 */
public class SocialEquityLogLocalServiceImpl
	extends SocialEquityLogLocalServiceBaseImpl {

	public void addEquityLogs(
			long userId, long assetEntryId, String actionId)
		throws PortalException, SystemException {

		if (!PropsValues.SOCIAL_EQUITY_EQUITY_LOG_ENABLED) {
			return;
		}

		User user = userPersistence.findByPrimaryKey(userId);

		AssetEntry assetEntry = assetEntryPersistence.findByPrimaryKey(
			assetEntryId);

		User assetEntryUser = null;

		try {
			assetEntryUser = userPersistence.findByPrimaryKey(
				assetEntry.getUserId());
		}
		catch (NoSuchUserException nsue) {
		}

		long groupId = assetEntry.getGroupId();

		List<SocialEquitySetting> equitySettings =
			socialEquitySettingLocalService.getEquitySettings(
				groupId, assetEntry.getClassNameId(), actionId);

		for (SocialEquitySetting equitySetting : equitySettings) {
			if (checkSocialEquityEnabled(
				groupId, assetEntry.getClassName(), equitySetting.getType())) {

				addEquityLog(user, assetEntry, assetEntryUser, equitySetting);
			}
		}
	}

	public void addEquityLogs(
			long userId, String className, long classPK, String actionId)
		throws PortalException, SystemException {

		if (!PropsValues.SOCIAL_EQUITY_EQUITY_LOG_ENABLED) {
			return;
		}

		AssetEntry assetEntry = null;

		try {
			assetEntry = assetEntryLocalService.getEntry(
				className, classPK);
		}
		catch (NoSuchEntryException nsee) {
			return;
		}

		addEquityLogs(userId, assetEntry.getEntryId(), actionId);
	}

	public void checkEquityLogs() throws SystemException {
		int validity = getEquityDate();

		if (!PropsValues.SOCIAL_EQUITY_EQUITY_LOG_ENABLED) {
			return;
		}

		runCheckSQL(_CHECK_SOCIAL_EQUITY_ASSET_ENTRY_IQ, validity);

		assetEntryPersistence.clearCache();

		runCheckSQL(_CHECK_SOCIAL_EQUITY_USER, validity);
		runCheckSQL(_CHECK_SOCIAL_EQUITY_USER_CQ, validity);
		runCheckSQL(_CHECK_SOCIAL_EQUITY_USER_PQ, validity);

		userPersistence.clearCache();

		runCheckSQL(_CHECK_SOCIAL_EQUITY_LOGS, validity);

		socialEquityLogPersistence.clearCache();

		updateRanks();
	}

	public void deactivateEquityLogs(long assetEntryId)
		throws PortalException, SystemException {

		if (!PropsValues.SOCIAL_EQUITY_EQUITY_LOG_ENABLED) {
			return;
		}

		SocialEquityAssetEntry equityAssetEntry = null;

		try {
			equityAssetEntry =
				socialEquityAssetEntryPersistence.findByAssetEntryId(
					assetEntryId);

			socialEquityAssetEntryPersistence.removeByAssetEntryId(
				assetEntryId);
		}
		catch (NoSuchEquityAssetEntryException nseaee) {
			return;
		}

		User user = null;

		try {
			user = userPersistence.findByPrimaryKey(
				equityAssetEntry.getUserId());

			if (!user.isDefaultUser()) {
				SocialEquityValue socialEquityValue = new SocialEquityValue(
					-equityAssetEntry.getInformationK(),
					-equityAssetEntry.getInformationB());

				incrementSocialEquityUser_CQ(
					equityAssetEntry.getGroupId(), user.getUserId(),
					socialEquityValue);
			}
		}
		catch (NoSuchUserException nsue) {
		}

		List<SocialEquityLog> equityLogs =
			socialEquityLogPersistence.findByAEI_T_A(
				assetEntryId, SocialEquitySettingConstants.TYPE_INFORMATION,
				true);

		for (SocialEquityLog equityLog : equityLogs) {
			equityLog.setActive(false);

			socialEquityLogPersistence.update(equityLog, false);
		}
	}

	public void deactivateEquityLogs(
			long userId, long assetEntryId, String actionId)
		throws PortalException, SystemException {

		if (!PropsValues.SOCIAL_EQUITY_EQUITY_LOG_ENABLED) {
			return;
		}

		User user = userPersistence.findByPrimaryKey(userId);

		AssetEntry assetEntry = assetEntryPersistence.findByPrimaryKey(
			assetEntryId);

		// Information Equity

		if (checkSocialEquityEnabled(
			assetEntry.getGroupId(), assetEntry.getClassName(),
			SocialEquitySettingConstants.TYPE_INFORMATION)) {

			List<SocialEquityLog> equityLogs =
				socialEquityLogPersistence.findByAEI_AID_A_T(
					assetEntryId, actionId, true,
					SocialEquitySettingConstants.TYPE_INFORMATION);

			SocialEquityValue socialEquityValue = new SocialEquityValue(0,0);

			for (SocialEquityLog equityLog : equityLogs) {
				double k = calculateK(
					equityLog.getValue(),equityLog.getLifespan());
				double b = calculateB(
					equityLog.getActionDate(), equityLog.getValue(),
					equityLog.getLifespan());

				socialEquityValue.subtract(new SocialEquityValue(k,b));

				socialEquityLogPersistence.remove(equityLog);
			}

			socialEquityLogLocalService.incrementSocialEquityAssetEntry_IQ(
				assetEntryId, socialEquityValue);

			socialEquityLogLocalService.incrementSocialEquityUser_CQ(
				assetEntry.getGroupId(), assetEntry.getUserId(),
				socialEquityValue);
		}

		// Participation Equity

		if (checkSocialEquityEnabled(
			assetEntry.getGroupId(), assetEntry.getClassName(),
			SocialEquitySettingConstants.TYPE_PARTICIPATION)) {

			List<SocialEquityLog> equityLogs =
				socialEquityLogPersistence.findByU_AID_A_T(
					userId, actionId, true,
					SocialEquitySettingConstants.TYPE_PARTICIPATION);

			SocialEquityValue socialEquityValue = new SocialEquityValue(0,0);

			for (SocialEquityLog equityLog : equityLogs) {
				double k = calculateK(
					equityLog.getValue(),equityLog.getLifespan());
				double b = calculateB(
					equityLog.getActionDate(), equityLog.getValue(),
					equityLog.getLifespan());

				socialEquityValue.subtract(new SocialEquityValue(k,b));

				socialEquityLogPersistence.remove(equityLog);
			}

			socialEquityLogLocalService.incrementSocialEquityUser_PQ(
				user.getGroup().getGroupId(), userId, socialEquityValue);
		}
	}

	public void deactivateEquityLogs(
			long userId, String className, long classPK, String actionId)
		throws PortalException, SystemException {

		if (!PropsValues.SOCIAL_EQUITY_EQUITY_LOG_ENABLED) {
			return;
		}

		AssetEntry assetEntry = null;

		try {
			assetEntry = assetEntryLocalService.getEntry(
				className, classPK);
		}
		catch (NoSuchEntryException nsee) {
			return;
		}

		deactivateEquityLogs(userId, assetEntry.getEntryId(), actionId);
	}

	@BufferedIncrement(incrementClass = SocialEquityIncrement.class)
	public void incrementSocialEquityAssetEntry_IQ(
			long assetEntryId, SocialEquityValue socialEquityValue)
		throws SystemException {

		AssetEntry assetEntry = assetEntryPersistence.fetchByPrimaryKey(
			assetEntryId);

		assetEntry.updateSocialInformationEquity(socialEquityValue.getValue());

		int count = socialEquityAssetEntryPersistence.countByAssetEntryId(
			assetEntryId);

		if (count == 0) {
			addSocialEquityAssetEntry(assetEntry);
		}

		String sql = CustomSQLUtil.get(_UPDATE_SOCIAL_EQUITY_ASSET_ENTRY_IQ);

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$ASSET_ENTRY_ID$]",
				"[$INFORMATION_B$]",
				"[$INFORMATION_K$]"
			},
			new String[] {
				String.valueOf(assetEntryId),
				String.valueOf(socialEquityValue.getB()),
				String.valueOf(socialEquityValue.getK())
			});

		runSQL(sql);
	}

	@BufferedIncrement(incrementClass = SocialEquityIncrement.class)
	public void incrementSocialEquityUser_CQ(
			long groupId, long userId, SocialEquityValue socialEquityValue)
		throws PortalException, SystemException {

		User user = userLocalService.getUser(userId);

		int count = socialEquityUserPersistence.countByG_U(groupId, userId);

		if (count == 0) {
			addSocialEquityUser(groupId, user);
		}

		user.updateSocialContributionEquity(socialEquityValue.getValue());

		String sql = CustomSQLUtil.get(_UPDATE_SOCIAL_EQUITY_USER_CQ);

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$CONTRIBUTION_B$]",
				"[$CONTRIBUTION_K$]",
				"[$GROUP_ID$]",
				"[$USER_ID$]"
			},
			new String[] {
				String.valueOf(socialEquityValue.getB()),
				String.valueOf(socialEquityValue.getK()),
				String.valueOf(groupId),
				String.valueOf(userId)
			});

		runSQL(sql);
	}

	@BufferedIncrement(incrementClass = SocialEquityIncrement.class)
	public void incrementSocialEquityUser_PQ(
			long groupId, long userId, SocialEquityValue socialEquityValue)
		throws PortalException, SystemException {

		User user = userLocalService.getUser(userId);

		int count = socialEquityUserPersistence.countByG_U(groupId, userId);

		if (count == 0) {
			addSocialEquityUser(groupId, user);
		}

		user.updateSocialParticipationEquity(socialEquityValue.getValue());

		String sql = CustomSQLUtil.get(_UPDATE_SOCIAL_EQUITY_USER_PQ);

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$GROUP_ID$]",
				"[$PARTICIPATION_B$]",
				"[$PARTICIPATION_K$]",
				"[$USER_ID$]"
			},
			new String[] {
				String.valueOf(groupId),
				String.valueOf(socialEquityValue.getB()),
				String.valueOf(socialEquityValue.getK()),
				String.valueOf(userId)
			});

		runSQL(sql);
	}

	public void updateRanks() {
		DataSource dataSource = socialEquityLogPersistence.getDataSource();

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		UpdateRanksHandler updateRanksHandler = new UpdateRanksHandler(
			jdbcTemplate);

		String sql = CustomSQLUtil.get(_FIND_SOCIAL_EQUITY_USER);

		sql = StringUtil.replace(
			sql, "[$ACTION_DATE$]", String.valueOf(getEquityDate()));

		jdbcTemplate.query(sql, updateRanksHandler);

		updateRanksHandler.flush();
	}

	public void updateRanks(long groupId) {
		DataSource dataSource = socialEquityLogPersistence.getDataSource();

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		UpdateRanksHandler updateRanksHandler = new UpdateRanksHandler(
			jdbcTemplate);

		String sql = CustomSQLUtil.get(_FIND_SOCIAL_EQUITY_USER_BY_GROUP);

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$ACTION_DATE$]",
				"[$GROUP_ID$]"
			},
			new String[] {
				String.valueOf(getEquityDate()),
				String.valueOf(groupId)
			});

		jdbcTemplate.query(sql, updateRanksHandler);

		updateRanksHandler.flush();
	}

	protected void addEquityLog(
			User user, AssetEntry assetEntry, User assetEntryUser,
			SocialEquitySetting equitySetting)
		throws PortalException, SystemException {

		if (!checkActionRestrictions(
			user.getUserId(), assetEntry.getEntryId(), equitySetting)) {

			return;
		}

		int actionDate = getEquityDate();

		double k = calculateK(
			equitySetting.getValue(), equitySetting.getLifespan());
		double b = calculateB(
			actionDate, equitySetting.getValue(), equitySetting.getLifespan());

		SocialEquityValue socialEquity = new SocialEquityValue(k, b);

		if (equitySetting.getType() ==
				SocialEquitySettingConstants.TYPE_INFORMATION) {

			socialEquityLogLocalService.incrementSocialEquityAssetEntry_IQ(
				assetEntry.getEntryId(), socialEquity);

			if ((assetEntryUser != null) && !assetEntryUser.isDefaultUser()) {
				socialEquityLogLocalService.incrementSocialEquityUser_CQ(
					assetEntry.getGroupId(), assetEntryUser.getUserId(),
					socialEquity);
			}
		}
		else if (equitySetting.getType() ==
					SocialEquitySettingConstants.TYPE_PARTICIPATION) {

			if (!user.isDefaultUser()) {
				socialEquityLogLocalService.incrementSocialEquityUser_PQ(
					assetEntry.getGroupId(), user.getUserId(), socialEquity);
			}
		}

		long equityLogId = counterLocalService.increment();

		SocialEquityLog equityLog = socialEquityLogPersistence.create(
			equityLogId);

		equityLog.setGroupId(assetEntry.getGroupId());
		equityLog.setCompanyId(user.getCompanyId());
		equityLog.setUserId(user.getUserId());
		equityLog.setAssetEntryId(assetEntry.getEntryId());
		equityLog.setActionId(equitySetting.getActionId());
		equityLog.setActionDate(actionDate);
		equityLog.setType(equitySetting.getType());
		equityLog.setValue(equitySetting.getValue());
		equityLog.setExpiration(actionDate + equitySetting.getLifespan());
		equityLog.setActive(true);

		socialEquityLogPersistence.update(equityLog, false);
	}

	protected void addSocialEquityAssetEntry(AssetEntry assetEntry)
		throws SystemException {

		String sql = CustomSQLUtil.get(_ADD_SOCIAL_EQUITY_ASSET_ENTRY);

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$ASSET_ENTRY_ID$]",
				"[$COMPANY_ID$]",
				"[$EQUITY_ASSET_ENTRY_ID$]",
				"[$GROUP_ID$]",
				"[$USER_ID$]"
			},
			new String[] {
				String.valueOf(assetEntry.getEntryId()),
				String.valueOf(assetEntry.getCompanyId()),
				String.valueOf(counterLocalService.increment()),
				String.valueOf(assetEntry.getGroupId()),
				String.valueOf(assetEntry.getUserId())
			});

		runSQL(sql);
	}

	protected void addSocialEquityUser(long groupId, User user)
		throws SystemException {

		String sql = CustomSQLUtil.get(_ADD_SOCIAL_EQUITY_USER);

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$COMPANY_ID$]",
				"[$EQUITY_USER_ID$]",
				"[$GROUP_ID$]",
				"[$USER_ID$]"
			},
			new String[] {
				String.valueOf(user.getCompanyId()),
				String.valueOf(counterLocalService.increment()),
				String.valueOf(groupId),
				String.valueOf(user.getUserId())
			});

		runSQL(sql);
	}

	protected double calculateB(int actionDate, int value, int lifespan) {
		return calculateK(value, lifespan) * (actionDate + lifespan) * -1;
	}

	protected double calculateEquity(int actionDate, double k, double b) {
		return k * actionDate + b;
	}

	protected double calculateK(int value, int lifespan) {
		if (lifespan == 0) {
			return 0;
		}

		return ((double)value / lifespan) * -1;
	}

	protected boolean checkActionRestrictions(
			long userId, long assetEntryId, SocialEquitySetting equitySetting)
		throws SystemException {

		if (equitySetting.getDailyLimit() < 0) {
			return false;
		}

		String actionId = equitySetting.getActionId();
		int actionDate = getEquityDate();
		int type = equitySetting.getType();

		// Duplicate

		if (socialEquityLogPersistence.countByU_AEI_AID_AD_A_T(
				userId, assetEntryId, actionId, actionDate, true, type) > 0) {

			return false;
		}

		// Unique

		if (equitySetting.isUniqueEntry()) {
			int count = 0;

			if (type == SocialEquitySettingConstants.TYPE_INFORMATION) {
				count = socialEquityLogPersistence.countByAEI_AID_A_T(
					assetEntryId, actionId, true, type);
			}
			else {
				count = socialEquityLogPersistence.countByU_AID_A_T(
					userId, actionId, true, type);
			}

			if (count > 0) {
				return false;
			}
		}

		// Daily limit

		if (equitySetting.getDailyLimit() == 0) {
			return true;
		}

		int count = 0;

		if (type == SocialEquitySettingConstants.TYPE_INFORMATION) {
			count = socialEquityLogPersistence.countByAEI_AID_AD_A_T(
				assetEntryId, actionId, actionDate, true, type);
		}
		else {
			count = socialEquityLogPersistence.countByU_AID_AD_A_T(
				userId, actionId, actionDate, true, type);
		}

		if (count < equitySetting.getDailyLimit()) {
			return true;
		}
		else {
			return false;
		}
	}

	protected boolean checkSocialEquityEnabled(
			long groupId, String className, int type)
		throws SystemException {

		if (!socialEquityGroupSettingLocalService.isSocialEquityEnabled(
			groupId, Group.class.getName(), type)) {

			return false;
		}

		return socialEquityGroupSettingLocalService.isSocialEquityEnabled(
			groupId, className, type);
	}

	protected int getEquityDate() {
		return getEquityDate(new Date());
	}

	protected int getEquityDate(Date date) {
		Calendar calendar = new GregorianCalendar(2010, Calendar.JANUARY, 1);

		return calendar.fieldDifference(date, Calendar.DATE);
	}

	protected void runCheckSQL(String sqlId, int validity)
		throws SystemException {

		String sql = CustomSQLUtil.get(sqlId);

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$TYPE_INFORMATION$]",
				"[$TYPE_PARTICIPATION$]",
				"[$EXPIRATION$]"
			},
			new String[] {
				String.valueOf(SocialEquitySettingConstants.TYPE_INFORMATION),
				String.valueOf(SocialEquitySettingConstants.TYPE_PARTICIPATION),
				String.valueOf(validity)
			});

		runSQL(sql);
	}

	private static final String _ADD_SOCIAL_EQUITY_ASSET_ENTRY =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".addSocialEquityAssetEntry";

	private static final String _ADD_SOCIAL_EQUITY_USER =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".addSocialEquityUser";

	private static final String _CHECK_SOCIAL_EQUITY_ASSET_ENTRY_IQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".checkSocialEquityAssetEntry_IQ";

	private static final String _CHECK_SOCIAL_EQUITY_LOGS =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".checkSocialEquityLogs";

	private static final String _CHECK_SOCIAL_EQUITY_USER =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".checkSocialEquityUser";

	private static final String _CHECK_SOCIAL_EQUITY_USER_CQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".checkSocialEquityUser_CQ";

	private static final String _CHECK_SOCIAL_EQUITY_USER_PQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".checkSocialEquityUser_PQ";

	private static final String _FIND_SOCIAL_EQUITY_USER =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".findSocialEquityUser";

	private static final String _FIND_SOCIAL_EQUITY_USER_BY_GROUP =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".findSocialEquityUserByGroup";

	private static final String _UPDATE_SOCIAL_EQUITY_ASSET_ENTRY_IQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".updateSocialEquityAssetEntry_IQ";

	private static final String _UPDATE_SOCIAL_EQUITY_USER_CQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".updateSocialEquityUser_CQ";

	private static final String _UPDATE_SOCIAL_EQUITY_USER_PQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".updateSocialEquityUser_PQ";

	private static final String _UPDATE_SOCIAL_EQUITY_USER_RANK =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".updateSocialEquityUserRank";

	private class UpdateRanksHandler implements RowCallbackHandler {

		public UpdateRanksHandler(JdbcTemplate jdbcTemplate) {
			_updateRanksSetter = new UpdateRanksSetter(jdbcTemplate);
		}

		public void flush() {
			_updateRanksSetter.flush();
		}

		public void processRow(ResultSet rs) throws SQLException {
			long equityUserId = rs.getLong("equityUserId");
			long groupId = rs.getLong("groupId");
			double equityValue = rs.getDouble("equityValue");

			if (groupId == _groupId) {
				if (equityValue == _equityValue) {
					_ties++;
				}
				else {
					_equityValue = equityValue;
					_rank = _rank + _ties + 1;
					_ties = 0;
				}
			}
			else {
				_groupId = groupId;
				_rank = 1;
				_ties = 0;
			}

			_updateRanksSetter.add(equityUserId, _rank);
		}

		private double _equityValue;
		private long _groupId;
		private long _rank;
		private long _ties;
		private UpdateRanksSetter _updateRanksSetter;

	}

	private class UpdateRanksSetter implements BatchPreparedStatementSetter {

		public UpdateRanksSetter(JdbcTemplate jdbcTemplate) {
			_jdbcTemplate = jdbcTemplate;
		}

		public void add(long equityUserId, long rank) {
			_sqlParams.add(new Long[] {equityUserId, rank});

			if (_sqlParams.size() >= 100) {
				flush();
			}
		}

		public int getBatchSize() {
			return _sqlParams.size();
		}

		public void flush() {
			try {
				_jdbcTemplate.batchUpdate(_sql, this);
			}
			catch (DataAccessException dae) {
				throw dae;
			}
			finally {
				_sqlParams.clear();
			}
		}

		public void setValues(PreparedStatement ps, int index)
			throws SQLException {

			Long[] sqlParams = _sqlParams.get(index);

			long equityUserId = sqlParams[0];
			long rank = sqlParams[1];

			ps.setLong(1, rank);
			ps.setLong(2, equityUserId);
			ps.setLong(3, rank);
		}

		private JdbcTemplate _jdbcTemplate;
		private String _sql = CustomSQLUtil.get(
			_UPDATE_SOCIAL_EQUITY_USER_RANK);
		private List<Long[]> _sqlParams = new ArrayList<Long[]>();

	}

}