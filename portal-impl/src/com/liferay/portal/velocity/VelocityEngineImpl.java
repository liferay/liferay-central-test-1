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

package com.liferay.portal.velocity;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.velocity.VelocityContext;
import com.liferay.portal.kernel.velocity.VelocityEngine;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;

/**
 * <a href="VelocityEngineImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Aug�
 */
public class VelocityEngineImpl implements VelocityEngine {

	public VelocityEngineImpl() {
	}

	public void flushTemplate(String resource) {
		StringResourceRepository stringResourceRepository =
			StringResourceLoader.getRepository();

		if (stringResourceRepository != null) {
			stringResourceRepository.removeStringResource(resource);
		}
	}

	public VelocityContext getEmptyContext() {
		return new VelocityContextImpl();
	}

	public VelocityContext getRestrictedToolsContext() {
		return _restrictedToolsContext;
	}

	public VelocityContext getStandardToolsContext() {
		return _standardToolsContext;
	}

	public VelocityContext getWrappedRestrictedToolsContext() {
		return new VelocityContextImpl(
			_restrictedToolsContext.getWrappedVelocityContext());
	}

	public VelocityContext getWrappedStandardToolsContext() {
		return new VelocityContextImpl(
			_standardToolsContext.getWrappedVelocityContext());
	}

	public void init() {
		_velocityEngine = new org.apache.velocity.app.VelocityEngine();

		LiferayResourceLoader.setListeners(
			PropsValues.VELOCITY_ENGINE_RESOURCE_LISTENERS);

		ExtendedProperties extendedProperties = new ExtendedProperties();

		extendedProperties.setProperty(_RESOURCE_LOADER, "string,servlet");

		extendedProperties.setProperty(
			"string." + _RESOURCE_LOADER + ".class",
			StringResourceLoader.class.getName());

		extendedProperties.setProperty(
			"servlet." + _RESOURCE_LOADER + ".class",
			LiferayResourceLoader.class.getName());

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.RESOURCE_MANAGER_CLASS,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_RESOURCE_MANAGER));

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.RESOURCE_MANAGER_CACHE_CLASS,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_RESOURCE_MANAGER_CACHE));

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.VM_LIBRARY,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_VELOCIMACRO_LIBRARY));

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_LOGGER));

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.RUNTIME_LOG_LOGSYSTEM +
				".log4j.category",
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_LOGGER_CATEGORY));

		_velocityEngine.setExtendedProperties(extendedProperties);

		try {
			_velocityEngine.init();

			_restrictedToolsContext = new VelocityContextImpl();

			VelocityVariables.insertHelperUtilities(
				_restrictedToolsContext,
				PropsValues.JOURNAL_TEMPLATE_VELOCITY_RESTRICTED_VARIABLES);

			_standardToolsContext = new VelocityContextImpl();

			VelocityVariables.insertHelperUtilities(
				_standardToolsContext, null);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public boolean mergeTemplate(
			String velocityTemplateId, VelocityContext velocityContext,
			Writer writer)
		throws SystemException, IOException {

		return mergeTemplate(velocityTemplateId, null, velocityContext, writer);
	}

	public boolean mergeTemplate(
			String velocityTemplateId, String velocityTemplateContent,
			VelocityContext velocityContext, Writer writer)
		throws SystemException, IOException {

		try {
			if ((Validator.isNotNull(velocityTemplateContent)) &&
				(!PropsValues.LAYOUT_TEMPLATE_CACHE_ENABLED ||
				 !resourceExists(velocityTemplateId))) {

				StringResourceRepository stringResourceRepository =
					StringResourceLoader.getRepository();

				stringResourceRepository.putStringResource(
					velocityTemplateId, velocityTemplateContent);

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Added " + velocityTemplateId +
							" to the Velocity template repository");
				}
			}

			VelocityContextImpl velocityContextImpl =
				(VelocityContextImpl)velocityContext;

			return _velocityEngine.mergeTemplate(
				velocityTemplateId, StringPool.UTF8,
				velocityContextImpl.getWrappedVelocityContext(), writer);
		}
		catch (IOException ioe) {
			throw ioe;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public boolean resourceExists(String resource) {
		return _velocityEngine.resourceExists(resource);
	}

	private static final String _RESOURCE_LOADER =
		org.apache.velocity.app.VelocityEngine.RESOURCE_LOADER;

	private static Log _log = LogFactoryUtil.getLog(VelocityEngineImpl.class);

	private VelocityContextImpl _restrictedToolsContext;
	private VelocityContextImpl _standardToolsContext;
	private org.apache.velocity.app.VelocityEngine _velocityEngine;

}