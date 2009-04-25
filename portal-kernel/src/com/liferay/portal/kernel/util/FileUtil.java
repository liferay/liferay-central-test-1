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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import java.util.List;
import java.util.Properties;

/**
 * <a href="FileUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 *
 */
public class FileUtil {

	public static void copyDirectory(
		String sourceDirName, String destinationDirName) {

		getFile().copyDirectory(sourceDirName, destinationDirName);
	}

	public static void copyDirectory(File source, File destination) {
		getFile().copyDirectory(source, destination);
	}

	public static void copyFile(String source, String destination) {
		getFile().copyFile(source, destination);
	}

	public static void copyFile(
		String source, String destination, boolean lazy) {

		getFile().copyFile(source, destination, lazy);
	}

	public static void copyFile(File source, File destination) {
		getFile().copyFile(source, destination);
	}

	public static void copyFile(File source, File destination, boolean lazy) {
		getFile().copyFile(source, destination, lazy);
	}

	public static File createTempFile() {
		return getFile().createTempFile();
	}

	public static File createTempFile(String extension) {
		return getFile().createTempFile(extension);
	}

	public static String createTempFileName() {
		return getFile().createTempFileName();
	}

	public static String createTempFileName(String extension) {
		return getFile().createTempFileName(extension);
	}

	public static boolean delete(String file) {
		return getFile().delete(file);
	}

	public static boolean delete(File file) {
		return getFile().delete(file);
	}

	public static void deltree(String directory) {
		getFile().deltree(directory);
	}

	public static void deltree(File directory) {
		getFile().deltree(directory);
	}

	public static boolean exists(String fileName) {
		return getFile().exists(fileName);
	}

	public static boolean exists(File file) {
		return getFile().exists(file);
	}

	public static String extractText(InputStream is, String fileExt) {
		return getFile().extractText(is, fileExt);
	}

	public static String getAbsolutePath(File file) {
		return getFile().getAbsolutePath(file);
	}

	public static byte[] getBytes(File file) throws IOException {
		return getFile().getBytes(file);
	}

	public static byte[] getBytes(InputStream is) throws IOException {
		return getFile().getBytes(is);
	}

	public static byte[] getBytes(InputStream is, int bufferSize)
		throws IOException {

		return getFile().getBytes(is);
	}

	public static String getExtension(String fileName) {
		return getFile().getExtension(fileName);
	}

	public static com.liferay.portal.kernel.util.File getFile() {
		return _file;
	}

	public static String getPath(String fullFileName) {
		return getFile().getPath(fullFileName);
	}

	public static String getShortFileName(String fullFileName) {
		return getFile().getShortFileName(fullFileName);
	}

	public static boolean isAscii(File file) throws IOException {
		return getFile().isAscii(file);
	}

	public static String[] listDirs(String fileName) {
		return getFile().listDirs(fileName);
	}

	public static String[] listDirs(File file) {
		return getFile().listDirs(file);
	}

	public static String[] listFiles(String fileName) {
		return getFile().listFiles(fileName);
	}

	public static String[] listFiles(File file) {
		return getFile().listFiles(file);
	}

	public static void mkdirs(String pathName) {
		getFile().mkdirs(pathName);
	}

	public static boolean move(
		String sourceFileName, String destinationFileName) {

		return getFile().move(sourceFileName, destinationFileName);
	}

	public static boolean move(File source, File destination) {
		return getFile().move(source, destination);
	}

	public static String read(String fileName) throws IOException {
		return getFile().read(fileName);
	}

	public static String read(File file) throws IOException {
		return getFile().read(file);
	}

	public static String read(File file, boolean raw) throws IOException {
		return getFile().read(file, raw);
	}

	public static String replaceSeparator(String fileName) {
		return getFile().replaceSeparator(fileName);
	}

	public static File[] sortFiles(File[] files) {
		return getFile().sortFiles(files);
	}

	public static String stripExtension(String fileName) {
		return getFile().stripExtension(fileName);
	}

	public static List<String> toList(Reader reader) {
		return getFile().toList(reader);
	}

	public static List<String> toList(String fileName) {
		return getFile().toList(fileName);
	}

	public static Properties toProperties(FileInputStream fis) {
		return getFile().toProperties(fis);
	}

	public static Properties toProperties(String fileName) {
		return getFile().toProperties(fileName);
	}

	public static void write(String fileName, String s) throws IOException {
		getFile().write(fileName, s);
	}

	public static void write(String fileName, String s, boolean lazy)
		throws IOException {

		getFile().write(fileName, s, lazy);
	}

	public static void write(
			String fileName, String s, boolean lazy, boolean append)
		throws IOException {

		getFile().write(fileName, s, lazy, append);
	}

	public static void write(String pathName, String fileName, String s)
		throws IOException {

		getFile().write(pathName, fileName, s);
	}

	public static void write(
			String pathName, String fileName, String s, boolean lazy)
		throws IOException {

		getFile().write(pathName, fileName, s, lazy);
	}

	public static void write(
			String pathName, String fileName, String s, boolean lazy,
			boolean append)
		throws IOException {

		getFile().write(pathName, fileName, s, lazy, append);
	}

	public static void write(File file, String s) throws IOException {
		getFile().write(file, s);
	}

	public static void write(File file, String s, boolean lazy)
		throws IOException {

		getFile().write(file, s, lazy);
	}

	public static void write(File file, String s, boolean lazy, boolean append)
		throws IOException {

		getFile().write(file, s, lazy, append);
	}

	public static void write(String fileName, byte[] bytes) throws IOException {
		getFile().write(fileName, bytes);
	}

	public static void write(File file, byte[] bytes) throws IOException {
		getFile().write(file, bytes);
	}

	public static void write(String fileName, InputStream is)
		throws IOException {

		getFile().write(fileName, is);
	}

	public static void write(File file, InputStream is) throws IOException {
		getFile().write(file, is);
	}

	public void setFile(com.liferay.portal.kernel.util.File file) {
		_file = file;
	}

	private static com.liferay.portal.kernel.util.File _file;

}