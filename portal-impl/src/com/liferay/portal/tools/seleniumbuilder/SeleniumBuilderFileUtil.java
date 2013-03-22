/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.seleniumbuilder;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.tools.servicebuilder.ServiceBuilder;

import java.io.File;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Michael Hashimoto
 */
public class SeleniumBuilderFileUtil {

	public SeleniumBuilderFileUtil(String baseDir) {
		_baseDir = baseDir;
	}

	public String getBaseDir() {
		return _baseDir;
	}

	public Set<String> getChildElementAttributeValues(
		Element element, String attributeName) {

		Set<String> childElementAttributeValues = new TreeSet<String>();

		List<Element> childElements = element.elements();

		if (childElements.isEmpty()) {
			return childElementAttributeValues;
		}

		for (Element childElement : childElements) {
			String childElementName = childElement.attributeValue(
				attributeName);

			if (childElementName != null) {
				int x = childElementName.lastIndexOf(StringPool.POUND);

				if (x != -1) {
					childElementAttributeValues.add(
						childElementName.substring(0, x));
				}
			}

			childElementAttributeValues.addAll(
				getChildElementAttributeValues(childElement, attributeName));
		}

		return childElementAttributeValues;
	}

	public String getClassName(String fileName) {
		String classSuffix = getClassSuffix(fileName);

		return getClassName(fileName, classSuffix);
	}

	public String getClassName(String fileName, String classSuffix) {
		return
			getPackageName(fileName) + "." +
				getSimpleClassName(fileName, classSuffix);
	}

	public String getClassSuffix(String fileName) {
		int x = fileName.indexOf(CharPool.PERIOD);

		String classSuffix = StringUtil.upperCaseFirstLetter(
			fileName.substring(x + 1));

		if (classSuffix.equals("Testcase")) {
			classSuffix = "TestCase";
		}
		else if (classSuffix.equals("Testsuite")) {
			classSuffix = "TestSuite";
		}

		return classSuffix;
	}

	public String getJavaFileName(String fileName) {
		String classSuffix = getClassSuffix(fileName);

		return getJavaFileName(fileName, classSuffix);
	}

	public String getJavaFileName(String fileName, String classSuffix) {
		return
			getPackagePath(fileName) + "/" +
				getSimpleClassName(fileName, classSuffix) + ".java";
	}

	public int getLocatorCount(Element rootElement) {
		String xml = rootElement.asXML();

		for (int i = 1;; i++) {
			if (xml.contains("${locator" + i + "}")) {
				continue;
			}

			if (i > 1) {
				i--;
			}

			return i;
		}
	}

	public String getName(String fileName) {
		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

		return fileName.substring(x + 1, y);
	}

	public String getNormalizedContent(String fileName) throws Exception {
		String content = readFile(fileName);

		if (content != null) {
			content = content.trim();
			content = StringUtil.replace(content, "\n", "");
			content = StringUtil.replace(content, "\r\n", "");
			content = StringUtil.replace(content, "\t", " ");
			content = content.replaceAll(" +", " ");
		}

		return content;
	}

	public String getPackageName(String fileName) {
		String packagePath = getPackagePath(fileName);

		return StringUtil.replace(
			packagePath, StringPool.SLASH, StringPool.PERIOD);
	}

	public String getPackagePath(String fileName) {
		int x = fileName.lastIndexOf(StringPool.SLASH);

		return fileName.substring(0, x);
	}

	public String getReturnType(String name) {
		if (name.startsWith("Is")) {
			return "boolean";
		}

		return "void";
	}

	public Element getRootElement(String fileName) throws Exception {
		String content = getNormalizedContent(fileName);

		Document document = SAXReaderUtil.read(content, true);

		Element rootElement = document.getRootElement();

		validate(fileName, rootElement);

		return rootElement;
	}

	public String getSimpleClassName(String fileName) {
		String classSuffix = getClassSuffix(fileName);

		return getSimpleClassName(fileName, classSuffix);
	}

	public String getSimpleClassName(String fileName, String classSuffix) {
		return getName(fileName) + classSuffix;
	}

	public String getVariableName(String name) {
		return TextFormatter.format(name, TextFormatter.I);
	}

	public String normalizeFileName(String fileName) {
		return StringUtil.replace(
			fileName, StringPool.BACK_SLASH, StringPool.SLASH);
	}

	public String readFile(String fileName) throws Exception {
		return FileUtil.read(getBaseDir() + "/" + fileName);
	}

	public void writeFile(String fileName, String content, boolean format)
		throws Exception {

		File file = new File(getBaseDir() + "-generated/" + fileName);

		if (format) {
			ServiceBuilder.writeFile(file, content);
		}
		else {
			System.out.println("Writing " + file);

			FileUtil.write(file, content);
		}
	}

	protected void validate(String fileName, Element rootElement)
		throws Exception {

		if (fileName.endsWith(".function")) {
			validateFunctionDocument(fileName, rootElement);
		}
		else if (fileName.endsWith(".macro")) {
			validateMacroDocument(fileName, rootElement);
		}
		else if (fileName.endsWith(".path")) {
			validatePathDocument(fileName, rootElement);
		}
	}

	protected void validateCommandElement(
		String fileName, Element commandElement,
		String[] allowedCommandChildElementNames,
		String[] allowedExecuteAttributeNames,
		String[] allowedExecuteChildElementNames) {

		List<Element> elements = commandElement.elements();

		if (elements.isEmpty()) {
			throw new IllegalArgumentException(fileName);
		}

		for (Element element : elements) {
			String elementName = element.getName();

			if (!ArrayUtil.contains(
					allowedCommandChildElementNames, elementName)) {

				throw new IllegalArgumentException(fileName);
			}

			if (elementName.equals("execute")) {
				validateExecuteElement(
					fileName, element, allowedExecuteAttributeNames, ".*",
					allowedExecuteChildElementNames);
			}
			else if (elementName.equals("if")) {
				validateIfElement(
					fileName, element, allowedCommandChildElementNames,
					allowedExecuteAttributeNames,
					allowedExecuteChildElementNames);
			}
			else if (elementName.equals("var")) {
				validateVarElement(fileName, element);
			}
			else {
				throw new IllegalArgumentException(fileName);
			}
		}
	}

	protected void validateExecuteElement(
		String fileName, Element executeElement,
		String[] allowedExecuteAttributeNames,
		String allowedExecuteAttributeValuesRegex,
		String[] allowedExecuteChildElementNames) {

		boolean hasAllowedAttributeName = true;

		List<Attribute> attributes = executeElement.attributes();

		for (Attribute attribute : attributes) {
			String attributeName = attribute.getName();

			if (ArrayUtil.contains(
					allowedExecuteAttributeNames, attributeName)) {

				hasAllowedAttributeName = true;

				break;
			}
		}

		if (!hasAllowedAttributeName) {
			throw new IllegalArgumentException(fileName);
		}

		String action = executeElement.attributeValue("action");
		String function = executeElement.attributeValue("function");
		String macro = executeElement.attributeValue("macro");
		String selenium = executeElement.attributeValue("selenium");
		String testCase = executeElement.attributeValue("test-case");
		String testSuite = executeElement.attributeValue("test-suite");

		if (Validator.isNotNull(action) &&
			action.matches(allowedExecuteAttributeValuesRegex)) {

			for (Attribute attribute : attributes) {
				String attributeName = attribute.getName();

				if (!attributeName.equals("action") &&
					!attributeName.startsWith("locator") &&
					!attributeName.startsWith("locator-key") &&
					!attributeName.startsWith("value")) {

					throw new IllegalArgumentException(fileName);
				}

				if (attributeName.equals("locator") ||
					attributeName.equals("locator-key") ||
					attributeName.equals("value")) {

					throw new IllegalArgumentException(fileName);
				}
			}
		}
		else if (Validator.isNotNull(function) &&
				function.matches(allowedExecuteAttributeValuesRegex)) {

			for (Attribute attribute : attributes) {
				String attributeName = attribute.getName();

				if (!attributeName.startsWith("locator") &&
					!attributeName.equals("function") &&
					!attributeName.startsWith("value")) {

					throw new IllegalArgumentException(fileName);
				}

				if (attributeName.equals("locator") ||
					attributeName.equals("value")) {

					throw new IllegalArgumentException(fileName);
				}
			}
		}
		else if (Validator.isNotNull(macro) &&
				 selenium.matches(allowedExecuteAttributeValuesRegex)) {

			if (attributes.size() != 1) {
				throw new IllegalArgumentException(fileName);
			}
		}
		else if (Validator.isNotNull(selenium) &&
				 selenium.matches(allowedExecuteAttributeValuesRegex)) {

			for (Attribute attribute : attributes) {
				String attributeName = attribute.getName();

				if (!attributeName.equals("argument1") &&
					!attributeName.equals("argument2") &&
					!attributeName.equals("selenium")) {

					throw new IllegalArgumentException(fileName);
				}
			}
		}
		else if (Validator.isNotNull(testCase) &&
				 selenium.matches(allowedExecuteAttributeValuesRegex)) {

			if (attributes.size() != 1) {
				throw new IllegalArgumentException(fileName);
			}
		}
		else if (Validator.isNotNull(testSuite) &&
				 selenium.matches(allowedExecuteAttributeValuesRegex)) {

			if (attributes.size() != 1) {
				throw new IllegalArgumentException(fileName);
			}
		}
		else {
			throw new IllegalArgumentException(fileName);
		}

		List<Element> elements = executeElement.elements();

		if (allowedExecuteChildElementNames.length == 0) {
			if (!elements.isEmpty()) {
				throw new IllegalArgumentException(fileName);
			}
		}
		else {
			for (Element element : elements) {
				String elementName = element.getName();

				if (elementName.equals("var")) {
					validateVarElement(fileName, element);
				}
				else {
					throw new IllegalArgumentException(fileName);
				}
			}
		}
	}

	protected void validateFunctionDocument(
		String fileName, Element rootElement) {

		if (!Validator.equals(rootElement.getName(), "definition")) {
			throw new IllegalArgumentException(fileName);
		}

		List<Element> elements = rootElement.elements();

		for (Element element : elements) {
			String elementName = element.getName();

			if (elementName.equals("command")) {
				if (Validator.isNull(element.attributeValue("name"))) {
					throw new IllegalArgumentException(fileName);
				}

				validateCommandElement(
					fileName, element, new String[] {"execute", "if"},
					new String[] {"function", "selenium"}, new String[0]);
			}
			else {
				throw new IllegalArgumentException(fileName);
			}
		}
	}

	protected void validateIfElement(
		String fileName, Element ifElement,
		String[] allowedCommandChildElementNames,
		String[] allowedExecuteAttributeNames,
		String[] allowedExecuteChildElementNames) {

		List<Element> elements = ifElement.elements();

		for (Element element : elements) {
			String elementName = element.getName();

			if (elementName.equals("condition")) {
				validateExecuteElement(
					fileName, element, allowedExecuteAttributeNames, ".*",
					allowedExecuteChildElementNames);
			}
			else if (elementName.equals("else") || elementName.equals("then")) {
				validateCommandElement(
					fileName, element, allowedCommandChildElementNames,
					allowedExecuteAttributeNames,
					allowedExecuteChildElementNames);
			}
			else {
				throw new IllegalArgumentException(fileName);
			}
		}
	}

	protected void validateMacroDocument(String fileName, Element rootElement) {
		if (!Validator.equals(rootElement.getName(), "definition")) {
			throw new IllegalArgumentException(fileName);
		}

		List<Element> elements = rootElement.elements();

		for (Element element : elements) {
			String elementName = element.getName();

			if (elementName.equals("command")) {
				if (Validator.isNull(element.attributeValue("name"))) {
					throw new IllegalArgumentException(fileName);
				}

				validateCommandElement(
					fileName, element, new String[] {"execute", "if", "var"},
					new String[] {"action", "macro"}, new String[] {"var"});
			}
			else if (elementName.equals("var")) {
				validateVarElement(fileName, element);
			}
			else {
				throw new IllegalArgumentException(fileName);
			}
		}
	}

	protected void validatePathDocument(String fileName, Element rootElement) {
		Element headElement = rootElement.element("head");

		Element titleElement = headElement.element("title");

		String title = titleElement.getText();

		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

		String shortFileName = fileName.substring(x + 1, y);

		if ((title == null) || !shortFileName.equals(title)) {
			throw new IllegalArgumentException(fileName);
		}

		Element bodyElement = rootElement.element("body");

		Element tableElement = bodyElement.element("table");

		Element theadElement = tableElement.element("thead");

		Element trElement = theadElement.element("tr");

		Element tdElement = trElement.element("td");

		String tdText = tdElement.getText();

		if ((tdText == null) || !shortFileName.equals(tdText)) {
			throw new IllegalArgumentException(fileName);
		}
	}

	protected void validateVarElement(String fileName, Element varElement) {
		List<Attribute> attributes = varElement.attributes();

		if (attributes.isEmpty()) {
			throw new IllegalArgumentException(fileName);
		}

		for (Attribute attribute : attributes) {
			String attributeName = attribute.getName();

			if (!attributeName.equals("name") &&
				!attributeName.equals("value")) {

				throw new IllegalArgumentException(fileName);
			}
		}

		List<Element> elements = varElement.elements();

		if (!elements.isEmpty()) {
			throw new IllegalArgumentException(fileName);
		}
	}

	private String _baseDir;

}