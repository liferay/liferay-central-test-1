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

package com.liferay.portal.json.serializer;

import com.liferay.portal.kernel.repository.model.RepositoryModel;

/**
 * @author Igor Spasic
 */
public class RepositoryModelJSONSerializer extends BaseModelJSONSerializer {

	public RepositoryModelJSONSerializer(
		String serializerClassName, Class type) {

		super(serializerClassName, type);
	}

	@Override
	public String toJSONString(Object object) {
		RepositoryModel repositoryModel = (RepositoryModel) object;

		return super.toJSONString(repositoryModel.getModel());
	}

}