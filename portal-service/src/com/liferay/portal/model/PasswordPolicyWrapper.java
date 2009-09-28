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

package com.liferay.portal.model;


/**
 * <a href="PasswordPolicySoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link PasswordPolicy}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordPolicy
 * @generated
 */
public class PasswordPolicyWrapper implements PasswordPolicy {
	public PasswordPolicyWrapper(PasswordPolicy passwordPolicy) {
		_passwordPolicy = passwordPolicy;
	}

	public long getPrimaryKey() {
		return _passwordPolicy.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_passwordPolicy.setPrimaryKey(pk);
	}

	public long getPasswordPolicyId() {
		return _passwordPolicy.getPasswordPolicyId();
	}

	public void setPasswordPolicyId(long passwordPolicyId) {
		_passwordPolicy.setPasswordPolicyId(passwordPolicyId);
	}

	public long getCompanyId() {
		return _passwordPolicy.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_passwordPolicy.setCompanyId(companyId);
	}

	public long getUserId() {
		return _passwordPolicy.getUserId();
	}

	public void setUserId(long userId) {
		_passwordPolicy.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.SystemException {
		return _passwordPolicy.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_passwordPolicy.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _passwordPolicy.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_passwordPolicy.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _passwordPolicy.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_passwordPolicy.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _passwordPolicy.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_passwordPolicy.setModifiedDate(modifiedDate);
	}

	public boolean getDefaultPolicy() {
		return _passwordPolicy.getDefaultPolicy();
	}

	public boolean isDefaultPolicy() {
		return _passwordPolicy.isDefaultPolicy();
	}

	public void setDefaultPolicy(boolean defaultPolicy) {
		_passwordPolicy.setDefaultPolicy(defaultPolicy);
	}

	public java.lang.String getName() {
		return _passwordPolicy.getName();
	}

	public void setName(java.lang.String name) {
		_passwordPolicy.setName(name);
	}

	public java.lang.String getDescription() {
		return _passwordPolicy.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_passwordPolicy.setDescription(description);
	}

	public boolean getChangeable() {
		return _passwordPolicy.getChangeable();
	}

	public boolean isChangeable() {
		return _passwordPolicy.isChangeable();
	}

	public void setChangeable(boolean changeable) {
		_passwordPolicy.setChangeable(changeable);
	}

	public boolean getChangeRequired() {
		return _passwordPolicy.getChangeRequired();
	}

	public boolean isChangeRequired() {
		return _passwordPolicy.isChangeRequired();
	}

	public void setChangeRequired(boolean changeRequired) {
		_passwordPolicy.setChangeRequired(changeRequired);
	}

	public long getMinAge() {
		return _passwordPolicy.getMinAge();
	}

	public void setMinAge(long minAge) {
		_passwordPolicy.setMinAge(minAge);
	}

	public boolean getCheckSyntax() {
		return _passwordPolicy.getCheckSyntax();
	}

	public boolean isCheckSyntax() {
		return _passwordPolicy.isCheckSyntax();
	}

	public void setCheckSyntax(boolean checkSyntax) {
		_passwordPolicy.setCheckSyntax(checkSyntax);
	}

	public boolean getAllowDictionaryWords() {
		return _passwordPolicy.getAllowDictionaryWords();
	}

	public boolean isAllowDictionaryWords() {
		return _passwordPolicy.isAllowDictionaryWords();
	}

	public void setAllowDictionaryWords(boolean allowDictionaryWords) {
		_passwordPolicy.setAllowDictionaryWords(allowDictionaryWords);
	}

	public int getMinLength() {
		return _passwordPolicy.getMinLength();
	}

	public void setMinLength(int minLength) {
		_passwordPolicy.setMinLength(minLength);
	}

	public boolean getHistory() {
		return _passwordPolicy.getHistory();
	}

	public boolean isHistory() {
		return _passwordPolicy.isHistory();
	}

	public void setHistory(boolean history) {
		_passwordPolicy.setHistory(history);
	}

	public int getHistoryCount() {
		return _passwordPolicy.getHistoryCount();
	}

	public void setHistoryCount(int historyCount) {
		_passwordPolicy.setHistoryCount(historyCount);
	}

	public boolean getExpireable() {
		return _passwordPolicy.getExpireable();
	}

	public boolean isExpireable() {
		return _passwordPolicy.isExpireable();
	}

	public void setExpireable(boolean expireable) {
		_passwordPolicy.setExpireable(expireable);
	}

	public long getMaxAge() {
		return _passwordPolicy.getMaxAge();
	}

	public void setMaxAge(long maxAge) {
		_passwordPolicy.setMaxAge(maxAge);
	}

	public long getWarningTime() {
		return _passwordPolicy.getWarningTime();
	}

	public void setWarningTime(long warningTime) {
		_passwordPolicy.setWarningTime(warningTime);
	}

	public int getGraceLimit() {
		return _passwordPolicy.getGraceLimit();
	}

	public void setGraceLimit(int graceLimit) {
		_passwordPolicy.setGraceLimit(graceLimit);
	}

	public boolean getLockout() {
		return _passwordPolicy.getLockout();
	}

	public boolean isLockout() {
		return _passwordPolicy.isLockout();
	}

	public void setLockout(boolean lockout) {
		_passwordPolicy.setLockout(lockout);
	}

	public int getMaxFailure() {
		return _passwordPolicy.getMaxFailure();
	}

	public void setMaxFailure(int maxFailure) {
		_passwordPolicy.setMaxFailure(maxFailure);
	}

	public long getLockoutDuration() {
		return _passwordPolicy.getLockoutDuration();
	}

	public void setLockoutDuration(long lockoutDuration) {
		_passwordPolicy.setLockoutDuration(lockoutDuration);
	}

	public boolean getRequireUnlock() {
		return _passwordPolicy.getRequireUnlock();
	}

	public boolean isRequireUnlock() {
		return _passwordPolicy.isRequireUnlock();
	}

	public void setRequireUnlock(boolean requireUnlock) {
		_passwordPolicy.setRequireUnlock(requireUnlock);
	}

	public long getResetFailureCount() {
		return _passwordPolicy.getResetFailureCount();
	}

	public void setResetFailureCount(long resetFailureCount) {
		_passwordPolicy.setResetFailureCount(resetFailureCount);
	}

	public com.liferay.portal.model.PasswordPolicy toEscapedModel() {
		return _passwordPolicy.toEscapedModel();
	}

	public boolean isNew() {
		return _passwordPolicy.isNew();
	}

	public boolean setNew(boolean n) {
		return _passwordPolicy.setNew(n);
	}

	public boolean isCachedModel() {
		return _passwordPolicy.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_passwordPolicy.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _passwordPolicy.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_passwordPolicy.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _passwordPolicy.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _passwordPolicy.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_passwordPolicy.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _passwordPolicy.clone();
	}

	public int compareTo(com.liferay.portal.model.PasswordPolicy passwordPolicy) {
		return _passwordPolicy.compareTo(passwordPolicy);
	}

	public int hashCode() {
		return _passwordPolicy.hashCode();
	}

	public java.lang.String toString() {
		return _passwordPolicy.toString();
	}

	public java.lang.String toXmlString() {
		return _passwordPolicy.toXmlString();
	}

	public PasswordPolicy getWrappedPasswordPolicy() {
		return _passwordPolicy;
	}

	private PasswordPolicy _passwordPolicy;
}