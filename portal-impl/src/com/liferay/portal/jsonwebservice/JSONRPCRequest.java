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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import jodd.servlet.ServletUtil;

/**
 * @author Igor Spasic
 */
public class JSONRPCRequest {
	
	public static JSONRPCRequest detectJSONRPCRequest(
		HttpServletRequest request) {

		try {
			String requestBody = ServletUtil.readRequestBody(request);

			if (Validator.isNull(requestBody)) {
				return null;
			}

			if (!requestBody.startsWith(StringPool.OPEN_CURLY_BRACE)) {
				return null;
			}

			if (!requestBody.endsWith(StringPool.CLOSE_CURLY_BRACE)) {
				return null;
			}

			JSONDeserializer<HashMap<Object, Object>> jsonDeserializer =
				JSONFactoryUtil.createJSONDeserializer();

			jsonDeserializer.use(null, HashMap.class);
			jsonDeserializer.use("parameters", HashMap.class);

			HashMap<Object, Object> requestBodyMap =
				jsonDeserializer.deserialize(requestBody);

			JSONRPCRequest jsonrpcRequest = new JSONRPCRequest();
			
			jsonrpcRequest._id = (Integer)requestBodyMap.get("id");
			jsonrpcRequest._jsonrpc = (String)requestBodyMap.get("jsonrpc");
			jsonrpcRequest._method = (String)requestBodyMap.get("method");
			jsonrpcRequest._parameters =
				(Map<String, ?>)requestBodyMap.get("params");

			if (Validator.isNull(jsonrpcRequest._method)) {
				return null;
			}

			return jsonrpcRequest;
		}
		catch (Exception e) {
			return null;
		}
	}

	public Integer getId() {
		return _id;
	}

	public String getJsonrpc() {
		return _jsonrpc;
	}

	public String getMethod() {
		return _method;
	}

	public String getParameter(String name) {
		return String.valueOf(_parameters.get(name));
	}

	public Set<String> getParameterNames() {
		return _parameters.keySet();
	}

	public void setId(Integer id) {
		_id = id;
	}

	public void setJsonrpc(String jsonrpc) {
		_jsonrpc = jsonrpc;
	}

	public void setMethod(String method) {
		_method = method;
	}

	public void setParameters(Map<String, ?> parameters) {
		_parameters = parameters;
	}

	private Integer _id;
	private String _jsonrpc;
	private String _method;
	private Map<String, ?> _parameters;

}