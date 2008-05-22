/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.jcr.jackrabbit;

import com.liferay.portal.jcr.JCRFactory;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.SystemProperties;
import com.liferay.util.Time;

import java.io.File;
import java.io.IOException;

import javax.jcr.Credentials;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.api.JackrabbitRepository;
import org.apache.jackrabbit.core.TransientRepository;

/**
 * <a href="JCRFactoryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 *
 */
public class JCRFactoryImpl implements JCRFactory {

	public static final String REPOSITORY_ROOT = PropsUtil.get(
		PropsUtil.JCR_JACKRABBIT_REPOSITORY_ROOT);

	public static final String CONFIG_FILE_PATH = PropsUtil.get(
		PropsUtil.JCR_JACKRABBIT_CONFIG_FILE_PATH);

	public static final String REPOSITORY_HOME = PropsUtil.get(
		PropsUtil.JCR_JACKRABBIT_REPOSITORY_HOME);

	public static final String CREDENTIALS_USERNAME = PropsUtil.get(
		PropsUtil.JCR_JACKRABBIT_CREDENTIALS_USERNAME);

	public static final char[] CREDENTIALS_PASSWORD = GetterUtil.getString(
		PropsUtil.get(PropsUtil.JCR_JACKRABBIT_CREDENTIALS_PASSWORD)).
			toCharArray();

	public Session createSession(String workspaceName)
		throws RepositoryException {

		Credentials credentials = new SimpleCredentials(
			CREDENTIALS_USERNAME, CREDENTIALS_PASSWORD);

		Session session = null;

		try {
			session = _repository.login(credentials, workspaceName);
		}
		catch (RepositoryException re) {
			_log.error("Could not login to the workspace " + workspaceName);

			throw re;
		}

		return session;
	}

	public void initialize() throws RepositoryException {
		Session session = null;

		try {
			session = createSession(null);
		}
		catch (RepositoryException re) {
			_log.error("Could not initialize Jackrabbit");

			throw re;
		}
		finally {
			if (session != null) {
				session.logout();
			}
		}

		_initialized = true;
	}

	public void prepare() throws RepositoryException {
		try {
			File repositoryRoot = new File(JCRFactoryImpl.REPOSITORY_ROOT);

			if (repositoryRoot.exists()) {
				return;
			}

			repositoryRoot.mkdirs();

			File tempFile = new File(
				SystemProperties.get(SystemProperties.TMP_DIR) +
					File.separator + Time.getTimestamp());

			String repositoryXmlPath =
				"com/liferay/portal/jcr/jackrabbit/dependencies/" +
					"repository-ext.xml";

			ClassLoader classLoader = getClass().getClassLoader();

			if (classLoader.getResource(repositoryXmlPath) == null) {
				repositoryXmlPath =
					"com/liferay/portal/jcr/jackrabbit/dependencies/" +
						"repository.xml";
			}

			String content = StringUtil.read(classLoader, repositoryXmlPath);

			FileUtil.write(tempFile, content);

			FileUtil.copyFile(
				tempFile, new File(JCRFactoryImpl.CONFIG_FILE_PATH));

			tempFile.delete();
		}
		catch (IOException ioe) {
			_log.error("Could not prepare Jackrabbit directory");

			throw new RepositoryException(ioe);
		}
	}

	public void shutdown() throws RepositoryException {
		if (_initialized) {
			Session session = null;

			try {
				session = createSession(null);

				JackrabbitRepository repository =
					(JackrabbitRepository)session.getRepository();

				repository.shutdown();
			}
			catch (RepositoryException re) {
				_log.error("Could not shutdown Jackrabbit");

				throw re;
			}
		}

		_initialized = false;
	}

	protected JCRFactoryImpl() throws Exception {
		try {
			_repository = new TransientRepository(
				CONFIG_FILE_PATH, REPOSITORY_HOME);
		}
		catch (Exception e) {
			_log.error("Problem initializing Jackrabbit JCR.", e);

			throw e;
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Jackrabbit JCR intialized with config file path " +
					CONFIG_FILE_PATH + " and repository home " +
						REPOSITORY_HOME);
		}
	}

	private static Log _log = LogFactory.getLog(JCRFactoryImpl.class);

	private Repository _repository;
	private boolean _initialized;

}