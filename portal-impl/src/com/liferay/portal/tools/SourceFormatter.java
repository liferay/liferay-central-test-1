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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.FileImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.DirectoryScanner;

/**
 * <a href="SourceFormatter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SourceFormatter {

	public static void main(String[] args) {
		try {
			_readExclusions();

			_checkPersistenceTestSuite();
			_checkWebXML();
			_formatJava();
			_formatJSP();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String stripImports(
			String content, String packageDir, String className)
		throws IOException {

		int x = content.indexOf("import ");

		if (x == -1) {
			return content;
		}

		int y = content.indexOf("{", x);

		y = content.substring(0, y).lastIndexOf(";") + 1;

		String imports = _formatImports(content.substring(x, y));

		content =
			content.substring(0, x) + imports +
				content.substring(y + 1, content.length());

		Set<String> classes = ClassUtil.getClasses(
			new StringReader(content), className);

		x = content.indexOf("import ");

		y = content.indexOf("{", x);

		y = content.substring(0, y).lastIndexOf(";") + 1;

		imports = content.substring(x, y);

		StringBuilder sb = new StringBuilder();

		BufferedReader br = new BufferedReader(new StringReader(imports));

		String line = null;

		while ((line = br.readLine()) != null) {
			if (line.indexOf("import ") != -1) {
				int importX = line.indexOf(" ");
				int importY = line.lastIndexOf(".");

				String importPackage = line.substring(importX + 1, importY);
				String importClass = line.substring(
					importY + 1, line.length() - 1);

				if (!packageDir.equals(importPackage)) {
					if (!importClass.equals("*")) {
						if (classes.contains(importClass)) {
							sb.append(line);
							sb.append("\n");
						}
					}
					else {
						sb.append(line);
						sb.append("\n");
					}
				}
			}
		}

		imports = _formatImports(sb.toString());

		content =
			content.substring(0, x) + imports +
				content.substring(y + 1, content.length());

		return content;
	}

	public static void _checkPersistenceTestSuite() throws IOException {
		String basedir = "./portal-impl/test";

		if (!_fileUtil.exists(basedir)) {
			return;
		}

		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(basedir);
		ds.setIncludes(new String[] {"**\\*PersistenceTest.java"});

		ds.scan();

		String[] files = ds.getIncludedFiles();

		Set<String> persistenceTests = new HashSet<String>();

		for (String file : files) {
			String persistenceTest = file.substring(0, file.length() - 5);

			persistenceTest = persistenceTest.substring(
				persistenceTest.lastIndexOf(File.separator) + 1,
				persistenceTest.length());

			persistenceTests.add(persistenceTest);
		}

		String persistenceTestSuite = _fileUtil.read(
			basedir + "/com/liferay/portal/service/persistence/" +
				"PersistenceTestSuite.java");

		for (String persistenceTest : persistenceTests) {
			if (persistenceTestSuite.indexOf(persistenceTest) == -1) {
				System.out.println("PersistenceTestSuite: " + persistenceTest);
			}
		}
	}

	private static void _checkWebXML() throws IOException {
		String basedir = "./";

		if (_fileUtil.exists(basedir + "portal-impl")) {
			return;
		}

		String webXML = ContentUtil.get(
			"com/liferay/portal/deploy/dependencies/web.xml");

		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(basedir);
		ds.setIncludes(new String[] {"**\\web.xml"});

		ds.scan();

		String[] files = ds.getIncludedFiles();

		for (String file : files) {
			String content = _fileUtil.read(basedir + file);

			if (content.equals(webXML)) {
				System.out.println(file);
			}
		}
	}

	private static void _checkXSS(String fileName, String jspContent) {
		Matcher matcher = _xssPattern.matcher(jspContent);

		while (matcher.find()) {
			boolean xssVulnerable = false;

			String jspVariable = matcher.group(1);

			String inputVulnerability =
				"<input[^>]* value=\"<%= " + jspVariable + " %>";

			Pattern inputVulnerabilityPattern =
				Pattern.compile(inputVulnerability, Pattern.CASE_INSENSITIVE);

			Matcher inputVulnerabilityMatcher =
				inputVulnerabilityPattern.matcher(jspContent);

			if (inputVulnerabilityMatcher.find()) {
				xssVulnerable = true;
			}

			String anchorVulnerability = " href=\"<%= " + jspVariable + " %>";

			if (jspContent.indexOf(anchorVulnerability) != -1) {
				xssVulnerable = true;
			}

			String inlineStringVulnerability1 = "'<%= " + jspVariable + " %>";

			if (jspContent.indexOf(inlineStringVulnerability1) != -1) {
				xssVulnerable = true;
			}

			String inlineStringVulnerability2 = "(\"<%= " + jspVariable + " %>";

			if (jspContent.indexOf(inlineStringVulnerability2) != -1) {
				xssVulnerable = true;
			}

			String inlineStringVulnerability3 = " \"<%= " + jspVariable + " %>";

			if (jspContent.indexOf(inlineStringVulnerability3) != -1) {
				xssVulnerable = true;
			}

			String documentIdVulnerability = ".<%= " + jspVariable + " %>";

			if (jspContent.indexOf(documentIdVulnerability) != -1) {
				xssVulnerable = true;
			}

			if (xssVulnerable) {
				System.out.println(
					"(xss): " + fileName + " (" + jspVariable + ")");
			}
		}
	}

	public static String _formatImports(String imports) throws IOException {
		if ((imports.indexOf("/*") != -1) ||
			(imports.indexOf("*/") != -1) ||
			(imports.indexOf("//") != -1)) {

			return imports + "\n";
		}

		List<String> importsList = new ArrayList<String>();

		BufferedReader br = new BufferedReader(new StringReader(imports));

		String line = null;

		while ((line = br.readLine()) != null) {
			if (line.indexOf("import ") != -1) {
				if (!importsList.contains(line)) {
					importsList.add(line);
				}
			}
		}

		importsList = ListUtil.sort(importsList);

		StringBuilder sb = new StringBuilder();

		String temp = null;

		for (int i = 0; i < importsList.size(); i++) {
			String s = importsList.get(i);

			int pos = s.indexOf(".");

			pos = s.indexOf(".", pos + 1);

			if (pos == -1) {
				pos = s.indexOf(".");
			}

			String packageLevel = s.substring(7, pos);

			if ((i != 0) && (!packageLevel.equals(temp))) {
				sb.append("\n");
			}

			temp = packageLevel;

			sb.append(s);
			sb.append("\n");
		}

		return sb.toString();
	}

	private static void _formatJava() throws IOException {
		String basedir = "./";

		String copyright = _getCopyright();

		boolean portalJavaFiles = true;

		String[] files = null;

		if (_fileUtil.exists(basedir + "portal-impl")) {
			files = _getPortalJavaFiles();
		}
		else {
			portalJavaFiles = false;

			files = _getPluginJavaFiles();
		}

		for (int i = 0; i < files.length; i++) {
			File file = new File(basedir + files[i]);

			String content = _fileUtil.read(file);

			String className = file.getName();

			className = className.substring(0, className.length() - 5);

			String packagePath = files[i];

			int packagePathX = packagePath.indexOf(
				File.separator + "src" + File.separator);
			int packagePathY = packagePath.lastIndexOf(File.separator);

			if ((packagePathX + 5) >= packagePathY) {
				packagePath = StringPool.BLANK;
			}
			else {
				packagePath = packagePath.substring(
					packagePathX + 5, packagePathY);
			}

			packagePath = StringUtil.replace(
				packagePath, File.separator, StringPool.PERIOD);

			if (packagePath.endsWith(".model")) {
				if (content.indexOf(
						"extends " + className + "Model {") != -1) {

					continue;
				}
			}

			String newContent = _formatJavaContent(files[i], content);

			if (newContent.indexOf("$\n */") != -1) {
				System.out.println("*: " + files[i]);

				newContent = StringUtil.replace(
					newContent, "$\n */", "$\n *\n */");
			}

			if (newContent.indexOf(copyright) == -1) {
				System.out.println("(c): " + files[i]);
			}

			if (newContent.indexOf(className + ".java.html") == -1) {
				System.out.println("Java2HTML: " + files[i]);
			}

			newContent = stripImports(newContent, packagePath, className);

			if (newContent.indexOf(";\n/**") != -1) {
				newContent = StringUtil.replace(
					newContent,
					";\n/**",
					";\n\n/**");
			}

			if (newContent.indexOf("\t/*\n\t *") != -1) {
				newContent = StringUtil.replace(
					newContent,
					"\t/*\n\t *",
					"\t/**\n\t *");
			}

			if (newContent.indexOf("if(") != -1) {
				newContent = StringUtil.replace(
					newContent,
					"if(",
					"if (");
			}

			if (newContent.indexOf("while(") != -1) {
				newContent = StringUtil.replace(
					newContent,
					"while(",
					"while (");
			}

			if (newContent.indexOf("\n\n\n") != -1) {
				newContent = StringUtil.replace(
					newContent,
					"\n\n\n",
					"\n\n");
			}

			if (newContent.indexOf("*/\npackage ") != -1) {
				System.out.println("package: " + files[i]);
			}

			if (newContent.indexOf("    ") != -1) {
				if (!files[i].endsWith("StringPool.java")) {
					System.out.println("tab: " + files[i]);
				}
			}

			if (newContent.indexOf("  {") != -1) {
				System.out.println("{:" + files[i]);
			}

			if (!newContent.endsWith("\n\n}") &&
				!newContent.endsWith("{\n}")) {

				System.out.println("}: " + files[i]);
			}

			if (portalJavaFiles && className.endsWith("ServiceImpl") &&
				(newContent.indexOf("ServiceUtil.") != -1)) {

				System.out.println("ServiceUtil: " + files[i]);
			}

			if ((newContent != null) && !content.equals(newContent)) {
				_fileUtil.write(file, newContent);

				System.out.println(file);
			}
		}
	}

	private static String _formatJavaContent(String fileName, String content)
		throws IOException {

		boolean longLogFactoryUtil = false;

		StringBuilder sb = new StringBuilder();

		BufferedReader br = new BufferedReader(new StringReader(content));

		int lineCount = 0;

		String line = null;

		while ((line = br.readLine()) != null) {
			lineCount++;

			if (line.trim().length() == 0) {
				line = StringPool.BLANK;
			}

			line = StringUtil.trimTrailing(line);

			line = StringUtil.replace(
				line,
				new String[] {
					"* Copyright (c) 2000-2008 Liferay, Inc.",
					"* Copyright 2008 Sun Microsystems Inc."
				},
				new String[] {
					"* Copyright (c) 2000-2009 Liferay, Inc.",
					"* Copyright 2009 Sun Microsystems Inc."
				});

			sb.append(line);
			sb.append("\n");

			StringBuilder lineSB = new StringBuilder();

			int spacesPerTab = 4;

			for (char c : line.toCharArray()) {
				if (c == CharPool.TAB) {
					for (int i = 0; i < spacesPerTab; i++) {
						lineSB.append(CharPool.SPACE);
					}

					spacesPerTab = 4;
				}
				else {
					lineSB.append(c);

					spacesPerTab--;

					if (spacesPerTab <= 0) {
						spacesPerTab = 4;
					}
				}
			}

			line = lineSB.toString();

			String excluded = _exclusions.getProperty(
				StringUtil.replace(fileName, "\\", "/") + StringPool.AT +
					lineCount);

			if (excluded == null) {
				excluded = _exclusions.getProperty(
					StringUtil.replace(fileName, "\\", "/"));
			}

			if ((excluded == null) && (line.length() > 80) &&
				(!line.startsWith("import "))) {

				if (line.contains(
						"private static Log _log = LogFactoryUtil.getLog(")) {

					longLogFactoryUtil = true;
				}

				if (fileName.endsWith("Table.java") &&
					line.contains("String TABLE_SQL_CREATE = ")) {
				}
				else {
					System.out.println("> 80: " + fileName + " " + lineCount);
				}
			}
		}

		br.close();

		String newContent = sb.toString();

		if (newContent.endsWith("\n")) {
			newContent = newContent.substring(0, newContent.length() -1);
		}

		if (longLogFactoryUtil) {
			newContent = StringUtil.replace(
				newContent, "private static Log _log = ",
				"private static Log _log =\n\t\t");
		}

		return newContent;
	}

	private static void _formatJSP() throws IOException {
		String basedir = "./";

		List<String> list = new ArrayList<String>();

		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(basedir);
		ds.setExcludes(
			new String[] {
				"**\\aui\\css.jspf", "**\\null.jsp", "**\\tmp\\**",
				"**\\tools\\tck\\**"
			});
		ds.setIncludes(new String[] {"**\\*.jsp", "**\\*.jspf", "**\\*.vm"});

		ds.scan();

		list.addAll(ListUtil.fromArray(ds.getIncludedFiles()));

		String copyright = _getCopyright();

		String[] files = list.toArray(new String[list.size()]);

		for (int i = 0; i < files.length; i++) {
			File file = new File(basedir + files[i]);

			String content = _fileUtil.read(file);
			String newContent = _formatJSPContent(files[i], content);

			newContent = StringUtil.replace(
				newContent,
				new String[] {
					"<br/>", "\"/>", "\" >", "@page import", "\"%>", ")%>",
					"javascript: "
				},
				new String[] {
					"<br />", "\" />", "\">", "@ page import", "\" %>", ") %>",
					"javascript:"
				});

			newContent = StringUtil.replace(
				newContent,
				new String[] {
					"* Copyright (c) 2000-2008 Liferay, Inc.",
					"* Copyright 2008 Sun Microsystems Inc."
				},
				new String[] {
					"* Copyright (c) 2000-2009 Liferay, Inc.",
					"* Copyright 2009 Sun Microsystems Inc."
				});

			if (files[i].endsWith(".jsp")) {
				if (newContent.indexOf(copyright) == -1) {
					System.out.println("(c): " + files[i]);
				}
			}

			if (newContent.indexOf("alert('<%= LanguageUtil.") != -1) {
				newContent = StringUtil.replace(newContent,
					"alert('<%= LanguageUtil.",
					"alert('<%= UnicodeLanguageUtil.");
			}

			if (newContent.indexOf("alert(\"<%= LanguageUtil.") != -1) {
				newContent = StringUtil.replace(newContent,
					"alert(\"<%= LanguageUtil.",
					"alert(\"<%= UnicodeLanguageUtil.");
			}

			if (newContent.indexOf("confirm('<%= LanguageUtil.") != -1) {
				newContent = StringUtil.replace(newContent,
					"confirm('<%= LanguageUtil.",
					"confirm('<%= UnicodeLanguageUtil.");
			}

			if (newContent.indexOf("confirm(\"<%= LanguageUtil.") != -1) {
				newContent = StringUtil.replace(newContent,
					"confirm(\"<%= LanguageUtil.",
					"confirm(\"<%= UnicodeLanguageUtil.");
			}

			if (newContent.indexOf("    ") != -1) {
				if (!files[i].endsWith("template.vm")) {
					System.out.println("tab: " + files[i]);
				}
			}

			_checkXSS(files[i], content);

			if ((newContent != null) && !content.equals(newContent)) {
				_fileUtil.write(file, newContent);

				System.out.println(file);
			}
		}
	}

	private static String _formatJSPContent(String fileName, String content)
		throws IOException {

		StringBuilder sb = new StringBuilder();

		BufferedReader br = new BufferedReader(new StringReader(content));

		String line = null;

		while ((line = br.readLine()) != null) {
			if (line.trim().length() == 0) {
				line = StringPool.BLANK;
			}

			line = StringUtil.trimTrailing(line);

			sb.append(line);
			sb.append("\n");
		}

		br.close();

		content = sb.toString();

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() -1);
		}

		content = _formatTaglibQuotes(fileName, content, StringPool.QUOTE);
		content = _formatTaglibQuotes(fileName, content, StringPool.APOSTROPHE);

		return content;
	}

	private static String _formatTaglibQuotes(
		String fileName, String content, String quoteType) {

		String quoteFix = StringPool.APOSTROPHE;

	    if (quoteFix.equals(quoteType)) {
	    	quoteFix = StringPool.QUOTE;
	    }

	    Pattern pattern = Pattern.compile(_getTaglibRegex(quoteType));

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			int x = content.indexOf(quoteType + "<%=", matcher.start());
			int y = content.indexOf("%>" + quoteType, x);

			while ((x != -1) && (y != -1)) {
				String result = content.substring(x + 1, y + 2);

				if (result.indexOf(quoteType) != -1) {
					int lineCount = 1;

					char contentCharArray[] = content.toCharArray();

					for (int i = 0; i < x; i++) {
						if (contentCharArray[i] == CharPool.NEW_LINE) {
							lineCount++;
						}
					}

					if (result.indexOf(quoteFix) == -1) {
						StringBuilder sb = new StringBuilder();

						sb.append(content.substring(0, x));
						sb.append(quoteFix);
						sb.append(result);
						sb.append(quoteFix);
						sb.append(content.substring(y + 3, content.length()));

						content = sb.toString();
					}
					else {
						System.out.println(
							"taglib: " + fileName + " " + lineCount);
					}
				}

				x = content.indexOf(quoteType + "<%=", y);

				if (x > matcher.end()) {
					break;
				}

				y = content.indexOf("%>" + quoteType, x);
			}
		}

		return content;
	}

	private static String _getCopyright() throws IOException {
		try {
			return _fileUtil.read("copyright.txt");
		}
		catch (Exception e1) {
			try {
				return _fileUtil.read("../copyright.txt");
			}
			catch (Exception e2) {
				return _fileUtil.read("../../copyright.txt");
			}
		}
	}

	private static String[] _getPluginJavaFiles() {
		String basedir = "./";

		List<String> list = new ArrayList<String>();

		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(basedir);
		ds.setExcludes(
			new String[] {
				"**\\model\\*Clp.java", "**\\model\\*Model.java",
				"**\\model\\*Soap.java", "**\\model\\impl\\*ModelImpl.java",
				"**\\service\\*Service.java", "**\\service\\*ServiceClp.java",
				"**\\service\\*ServiceFactory.java",
				"**\\service\\*ServiceUtil.java",
				"**\\service\\ClpSerializer.java",
				"**\\service\\base\\*ServiceBaseImpl.java",
				"**\\service\\http\\*JSONSerializer.java",
				"**\\service\\http\\*ServiceHttp.java",
				"**\\service\\http\\*ServiceJSON.java",
				"**\\service\\http\\*ServiceSoap.java",
				"**\\service\\messaging\\*ClpMessageListener.java",
				"**\\service\\persistence\\*Finder.java",
				"**\\service\\persistence\\*Persistence.java",
				"**\\service\\persistence\\*PersistenceImpl.java",
				"**\\service\\persistence\\*Util.java", "**\\tmp\\**"
			});
		ds.setIncludes(new String[] {"**\\*.java"});

		ds.scan();

		list.addAll(ListUtil.fromArray(ds.getIncludedFiles()));

		return list.toArray(new String[list.size()]);
	}

	private static String[] _getPortalJavaFiles() {
		String basedir = "./";

		List<String> list = new ArrayList<String>();

		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(basedir);
		ds.setExcludes(
			new String[] {
				"**\\classes\\*", "**\\jsp\\*", "**\\tmp\\**",
				"**\\EARXMLBuilder.java", "**\\EJBXMLBuilder.java",
				"**\\PropsKeys.java", "**\\InstanceWrapperBuilder.java",
				"**\\ServiceBuilder.java", "**\\SourceFormatter.java",
				"**\\UserAttributes.java", "**\\WebKeys.java",
				"**\\*_IW.java", "**\\XHTMLComplianceFormatter.java",
				"**\\portal-service\\**\\model\\*Model.java",
				"**\\portal-service\\**\\model\\*Soap.java",
				"**\\model\\impl\\*ModelImpl.java",
				"**\\portal\\service\\**", "**\\portal-client\\**",
				"**\\portal-web\\classes\\**\\*.java",
				"**\\portal-web\\test\\**\\*Test.java",
				"**\\portlet\\**\\service\\**", "**\\tools\\ext_tmpl\\**",
				"**\\tools\\tck\\**"
			});
		ds.setIncludes(new String[] {"**\\*.java"});

		ds.scan();

		list.addAll(ListUtil.fromArray(ds.getIncludedFiles()));

		ds = new DirectoryScanner();

		ds.setBasedir(basedir);
		ds.setExcludes(
			new String[] {
				 "**\\portal-client\\**", "**\\tools\\ext_tmpl\\**",
				"**\\*_IW.java", "**\\test\\**\\*PersistenceTest.java"
			});
		ds.setIncludes(
			new String[] {
				"**\\com\\liferay\\portal\\service\\ServiceContext*.java",
				"**\\model\\BaseModel.java",
				"**\\model\\impl\\BaseModelImpl.java",
				"**\\service\\base\\PrincipalBean.java",
				"**\\service\\http\\*HttpTest.java",
				"**\\service\\http\\*SoapTest.java",
				"**\\service\\http\\TunnelUtil.java",
				"**\\service\\impl\\*.java", "**\\service\\jms\\*.java",
				"**\\service\\permission\\*.java",
				"**\\service\\persistence\\BasePersistence.java",
				"**\\service\\persistence\\BatchSession*.java",
				"**\\service\\persistence\\*FinderImpl.java",
				"**\\service\\persistence\\*Query.java",
				"**\\service\\persistence\\impl\\BasePersistenceImpl.java",
				"**\\portal-impl\\test\\**\\*.java",
				"**\\portal-service\\**\\liferay\\counter\\**.java",
				"**\\portal-service\\**\\liferay\\documentlibrary\\**.java",
				"**\\portal-service\\**\\liferay\\lock\\**.java",
				"**\\portal-service\\**\\liferay\\mail\\**.java",
				"**\\util-bridges\\**\\*.java"
			});

		ds.scan();

		list.addAll(ListUtil.fromArray(ds.getIncludedFiles()));

		return list.toArray(new String[list.size()]);
	}

	private static String _getTaglibRegex(String quoteType) {
		StringBuilder sb = new StringBuilder();

		sb.append("<(");

		for (int i = 0; i < _TAG_LIBRARIES.length; i++) {
			sb.append(_TAG_LIBRARIES[i]);
			sb.append(StringPool.PIPE);
		}

		sb.deleteCharAt(sb.length() - 1);
		sb.append("):([^>]|%>)*");
		sb.append(quoteType);
		sb.append("<%=.*");
		sb.append(quoteType);
		sb.append(".*%>");
		sb.append(quoteType);
		sb.append("([^>]|%>)*>");

		return sb.toString();
	}

	private static void _readExclusions() throws IOException {
		_exclusions = new Properties();

		ClassLoader classLoader = SourceFormatter.class.getClassLoader();

		String sourceFormatterExclusions = System.getProperty(
			"source-formatter-exclusions",
			"com/liferay/portal/tools/dependencies/" +
				"source_formatter_exclusions.properties");

		URL url = classLoader.getResource(sourceFormatterExclusions);

		if (url == null) {
			return;
		}

		InputStream is = url.openStream();

		_exclusions.load(is);

		is.close();
	}

	private static final String[] _TAG_LIBRARIES = new String[] {
		"aui", "c", "html", "jsp", "liferay-portlet", "liferay-security",
		"liferay-theme", "liferay-ui", "liferay-util", "portlet", "struts",
		"tiles"
	};

	private static FileImpl _fileUtil = FileImpl.getInstance();
	private static Properties _exclusions;
	private static Pattern _xssPattern = Pattern.compile(
		"String\\s+([^\\s]+)\\s*=\\s*ParamUtil\\.getString\\(");

}