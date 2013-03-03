/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.webdav;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.webdav.BaseResourceImpl;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;
import com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateServiceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Juan Fernández
 */
public class DDMWebDavUtil {

	public static final String TYPE_STRUCTURES = "Structures";

	public static final String TYPE_TEMPLATES = "Templates";

	public static int deleteResource(
			WebDAVRequest webDavRequest, String rootPath, String token,
			long classNameId)
		throws WebDAVException {

		try {
			Resource resource = getResource(
				webDavRequest, rootPath, token, classNameId);

			if (resource == null) {
				return HttpServletResponse.SC_NOT_FOUND;
			}

			Object model = resource.getModel();

			if (model instanceof DDMStructure) {
				DDMStructure structure = (DDMStructure)model;

				DDMStructureServiceUtil.deleteStructure(
					structure.getStructureId());

				return HttpServletResponse.SC_NO_CONTENT;
			}
			else if (model instanceof DDMTemplate) {
				DDMTemplate template = (DDMTemplate)model;

				DDMTemplateServiceUtil.deleteTemplate(template.getTemplateId());

				return HttpServletResponse.SC_NO_CONTENT;
			}
			else {
				return HttpServletResponse.SC_FORBIDDEN;
			}
		}
		catch (PortalException pe) {
			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public static Resource getResource(
			WebDAVRequest webDavRequest, String rootPath, String token,
			long classNameId)
		throws WebDAVException {

		try {
			String[] pathArray = webDavRequest.getPathArray();

			if (pathArray.length == 2) {
				String path = rootPath + webDavRequest.getPath();

				return new BaseResourceImpl(path, StringPool.BLANK, token);
			}
			else if (pathArray.length == 3) {
				String type = pathArray[2];

				return toResource(webDavRequest, type, rootPath, false);
			}
			else if (pathArray.length == 4) {
				String type = pathArray[2];
				String typeId = pathArray[3];

				if (type.equals(TYPE_STRUCTURES)) {
					try {
						DDMStructure ddmStructure = null;

						try {
							ddmStructure =
								DDMStructureLocalServiceUtil.getStructure(
									Long.valueOf(typeId));
						}
						catch (NumberFormatException nfe) {
							ddmStructure =
								DDMStructureLocalServiceUtil.getStructure(
									webDavRequest.getGroupId(), classNameId,
									typeId);
						}

						return DDMWebDavUtil.toResource(
							webDavRequest, ddmStructure, rootPath, false);
					}
					catch (NoSuchStructureException nsse) {
						return null;
					}
				}
				else if (type.equals(TYPE_TEMPLATES)) {
					try {
						DDMTemplate ddmTemplate = null;

						try {
							ddmTemplate =
								DDMTemplateLocalServiceUtil.getTemplate(
									Long.valueOf(typeId));
						}
						catch (NumberFormatException nfe) {
							ddmTemplate =
								DDMTemplateLocalServiceUtil.getTemplate(
									webDavRequest.getGroupId(), classNameId,
									typeId);
						}

						return DDMWebDavUtil.toResource(
							webDavRequest, ddmTemplate, rootPath, false);
					}
					catch (NoSuchTemplateException nste) {
						return null;
					}
				}
			}

			return null;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public static int putResource(
			WebDAVRequest webDavRequest, String rootPath, String token,
			long classNameId)
		throws WebDAVException {

		try {
			Resource resource = getResource(
				webDavRequest, rootPath, token, classNameId);

			if (resource == null) {
				return HttpServletResponse.SC_NOT_FOUND;
			}

			Object model = resource.getModel();

			if (model instanceof DDMStructure) {
				DDMStructure ddmStructure = (DDMStructure)model;

				HttpServletRequest request =
					webDavRequest.getHttpServletRequest();

				String xsd = StringUtil.read(request.getInputStream());

				DDMStructureServiceUtil.updateStructure(
					ddmStructure.getGroupId(),
					ddmStructure.getParentStructureId(),
					ddmStructure.getClassNameId(),
					ddmStructure.getStructureKey(), ddmStructure.getNameMap(),
					ddmStructure.getDescriptionMap(), xsd,
					new ServiceContext());

				return HttpServletResponse.SC_CREATED;
			}
			else if (model instanceof DDMTemplate) {
				DDMTemplate ddmTemplate = (DDMTemplate)model;

				HttpServletRequest request =
					webDavRequest.getHttpServletRequest();

				String script = StringUtil.read(request.getInputStream());

				DDMTemplateServiceUtil.updateTemplate(
					ddmTemplate.getTemplateId(), ddmTemplate.getNameMap(),
					ddmTemplate.getDescriptionMap(), ddmTemplate.getType(),
					ddmTemplate.getMode(), ddmTemplate.getLanguage(), script,
					ddmTemplate.isCacheable(), ddmTemplate.isSmallImage(),
					ddmTemplate.getSmallImageURL(), null, new ServiceContext());

				return HttpServletResponse.SC_CREATED;
			}
			else {
				return HttpServletResponse.SC_FORBIDDEN;
			}
		}
		catch (PortalException pe) {
			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public static Resource toResource(
		WebDAVRequest webDavRequest, DDMStructure structure, String rootPath,
		boolean appendPath) {

		String parentPath = rootPath + webDavRequest.getPath();

		String name = StringPool.BLANK;

		if (appendPath) {
			name = String.valueOf(structure.getStructureId());
		}

		return new DDMStructureResourceImpl(structure, parentPath, name);
	}

	public static Resource toResource(
		WebDAVRequest webDavRequest, DDMTemplate template, String rootPath,
		boolean appendPath) {

		String parentPath = rootPath + webDavRequest.getPath();

		String name = StringPool.BLANK;

		if (appendPath) {
			name = String.valueOf(template.getTemplateId());
		}

		return new DDMTemplateResourceImpl(template, parentPath, name);
	}

	public static Resource toResource(
		WebDAVRequest webDavRequest, String type, String rootPath,
		boolean appendPath) {

		String parentPath = rootPath + webDavRequest.getPath();

		String name = StringPool.BLANK;

		if (appendPath) {
			name = type;
		}

		Resource resource = new BaseResourceImpl(parentPath, name, type);

		resource.setModel(type);

		return resource;
	}

}