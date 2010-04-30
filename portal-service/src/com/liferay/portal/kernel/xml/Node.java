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

package com.liferay.portal.kernel.xml;

import java.io.IOException;
import java.io.Writer;

import java.util.List;

/**
 * <a href="Node.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface Node {

	public String asXML();

	public Node asXPathResult(Element parent);

	public Node detach();

	public Document getDocument();

	public String getName();

	public Element getParent();

	public String getPath();

	public String getPath(Element context);

	public String getStringValue();

	public String getText();

	public String getUniquePath();

	public String getUniquePath(Element context);

	public boolean hasContent();

	public boolean isReadOnly();

	public boolean matches(String xpathExpression);

	public Number numberValueOf(String xpathExpression);

	public List<Node> selectNodes(String xpathExpression);

	public List<Node> selectNodes(
		String xpathExpression, String comparisonXPathExpression);

	public List<Node> selectNodes(
		String xpathExpression, String comparisonXPathExpression,
		boolean removeDuplicates);

	public Object selectObject(String xpathExpression);

	public Node selectSingleNode(String xpathExpression);

	public void setName(String name);

	public void setText(String text);

	public boolean supportsParent();

	public String valueOf(String xpathExpression);

	public void write(Writer writer) throws IOException;

}