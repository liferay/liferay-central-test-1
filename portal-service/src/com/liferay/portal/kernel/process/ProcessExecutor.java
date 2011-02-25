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

package com.liferay.portal.kernel.process;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public class ProcessExecutor {

	public static <T extends Serializable> T execute(
			ProcessCallable<T> processCallable, String classPath)
		throws ProcessException {

		try {
			ProcessBuilder processBuilder = new ProcessBuilder(
				"java", "-cp", classPath, ProcessExecutor.class.getName());

			Process process = processBuilder.start();

			_writeObject(process.getOutputStream(), processCallable, true);

			int exitCode = process.waitFor();

			if (exitCode != 0) {
				throw new ProcessException(
					"Subprocess terminated with exit code " + exitCode);
			}

			InputStream errorInputStream = process.getErrorStream();

			if (errorInputStream.available() > 0) {
				ProcessException processException =
					(ProcessException)_readObject(errorInputStream, true);

				throw processException;
			}

			return (T)_readObject(process.getInputStream(), true);
		}
		catch (Exception e) {
			throw new ProcessException(e);
		}
	}

	public static void main(String[] arguments)
		throws ClassNotFoundException, IOException {

		try {
			ProcessCallable<?> processCallable =
				(ProcessCallable<?>)_readObject(System.in, false);

			Object result = processCallable.call();

			_writeObject(System.out, result, false);
		}
		catch (ProcessException pe) {
			_writeObject(System.err, pe, false);
		}
	}

	private static Object _readObject(InputStream inputStream, boolean close)
		throws ClassNotFoundException, IOException {

		ObjectInputStream objectInputStream = new ObjectInputStream(
			inputStream);

		try {
			return objectInputStream.readObject();
		}
		finally {
			if (close) {
				objectInputStream.close();
			}
		}
	}

	private static void _writeObject(
			OutputStream outputStream, Object object, boolean close)
		throws IOException {

		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
			outputStream);

		try {
			objectOutputStream.writeObject(object);
		}
		finally {
			if (close) {
				objectOutputStream.close();
			}
			else {
				objectOutputStream.flush();
			}
		}
	}

}