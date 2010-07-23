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

package com.liferay.portal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class DigesterImpl implements Digester {

	public String digest(String text) {
		return digest(Digester.DEFAULT_ALGORITHM, text);
	}

	public String digest(String algorithm, String ... text) {

		if (_BASE_64) {
			byte[] bytes = digestRaw(algorithm, text);

			return Base64.encode(bytes);
		}
		else {
			return digestHex(algorithm, text);
		}
	}

	public String digestHex(String text) {
		return digestHex(Digester.DEFAULT_ALGORITHM, text);
	}

	public String digestHex(String algorithm, String ... text) {
		byte[] bytes = digestRaw(algorithm, text);

		return Hex.encodeHexString(bytes);
	}

	public byte[] digestRaw(String text) {
		return digestRaw(Digester.DEFAULT_ALGORITHM, text);
	}

	public byte[] digestRaw(String algorithm, String ... text) {
		MessageDigest messageDigest = null;

		try{
			messageDigest = MessageDigest.getInstance(algorithm);

			StringBundler sb = new StringBundler();

			for (String t : text) {
				if (sb.length() > 0) {
					sb.append(":");
				}

				sb.append(t);
			}

			messageDigest.update(sb.toString().getBytes(Digester.ENCODING));
		}
		catch (NoSuchAlgorithmException nsae) {
			_log.error(nsae, nsae);
		}
		catch (UnsupportedEncodingException uee) {
			_log.error(uee, uee);
		}

		return messageDigest.digest();
	}

	private static final boolean _BASE_64 =
		PropsValues.PASSWORDS_DIGEST_ENCODING.equals("base64");

	private static Log _log = LogFactoryUtil.getLog(Digester.class);

}