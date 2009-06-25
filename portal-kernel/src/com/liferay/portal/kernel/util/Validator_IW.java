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

package com.liferay.portal.kernel.util;

/**
 * <a href="Validator_IW.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class Validator_IW {
	public static Validator_IW getInstance() {
		return _instance;
	}

	public boolean equals(java.lang.Object obj1, java.lang.Object obj2) {
		return Validator.equals(obj1, obj2);
	}

	public boolean isAddress(java.lang.String address) {
		return Validator.isAddress(address);
	}

	public boolean isAscii(char c) {
		return Validator.isAscii(c);
	}

	public boolean isChar(char c) {
		return Validator.isChar(c);
	}

	public boolean isChar(java.lang.String s) {
		return Validator.isChar(s);
	}

	public boolean isDate(int month, int day, int year) {
		return Validator.isDate(month, day, year);
	}

	public boolean isDigit(char c) {
		return Validator.isDigit(c);
	}

	public boolean isDigit(java.lang.String s) {
		return Validator.isDigit(s);
	}

	public boolean isDomain(java.lang.String domainName) {
		return Validator.isDomain(domainName);
	}

	public boolean isEmailAddress(java.lang.String emailAddress) {
		return Validator.isEmailAddress(emailAddress);
	}

	public boolean isEmailAddressSpecialChar(char c) {
		return Validator.isEmailAddressSpecialChar(c);
	}

	public boolean isGregorianDate(int month, int day, int year) {
		return Validator.isGregorianDate(month, day, year);
	}

	public boolean isHex(java.lang.String s) {
		return Validator.isHex(s);
	}

	public boolean isHTML(java.lang.String s) {
		return Validator.isHTML(s);
	}

	public boolean isIPAddress(java.lang.String ipAddress) {
		return Validator.isIPAddress(ipAddress);
	}

	public boolean isJulianDate(int month, int day, int year) {
		return Validator.isJulianDate(month, day, year);
	}

	public boolean isLUHN(java.lang.String number) {
		return Validator.isLUHN(number);
	}

	public boolean isName(java.lang.String name) {
		return Validator.isName(name);
	}

	public boolean isNotNull(java.lang.Object obj) {
		return Validator.isNotNull(obj);
	}

	public boolean isNotNull(java.lang.Long l) {
		return Validator.isNotNull(l);
	}

	public boolean isNotNull(java.lang.String s) {
		return Validator.isNotNull(s);
	}

	public boolean isNotNull(java.lang.Object[] array) {
		return Validator.isNotNull(array);
	}

	public boolean isNull(java.lang.Object obj) {
		return Validator.isNull(obj);
	}

	public boolean isNull(java.lang.Long l) {
		return Validator.isNull(l);
	}

	public boolean isNull(java.lang.String s) {
		return Validator.isNull(s);
	}

	public boolean isNull(java.lang.Object[] array) {
		return Validator.isNull(array);
	}

	public boolean isNumber(java.lang.String number) {
		return Validator.isNumber(number);
	}

	public boolean isPassword(java.lang.String password) {
		return Validator.isPassword(password);
	}

	public boolean isPhoneNumber(java.lang.String phoneNumber) {
		return Validator.isPhoneNumber(phoneNumber);
	}

	public boolean isVariableTerm(java.lang.String s) {
		return Validator.isVariableTerm(s);
	}

	public boolean isWhitespace(char c) {
		return Validator.isWhitespace(c);
	}

	public boolean isXml(java.lang.String s) {
		return Validator.isXml(s);
	}

	private Validator_IW() {
	}

	private static Validator_IW _instance = new Validator_IW();
}