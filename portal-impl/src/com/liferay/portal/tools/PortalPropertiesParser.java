package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.StringPool;
import jodd.io.StreamUtil;
import jodd.util.StringUtil;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <a href="PortalProperties2Doc.java.html"><b><i>View Source</i></b></a>
 */
public class PortalPropertiesParser implements XMLReader {
	
	public static final boolean PARAM_FULL_TOC = false;
	public static final int PARAM_MIN_LEFT = 2;
	public static final int PARAM_DEEP_RIGHT = 1;

	public static void main(String[] args) throws Exception {

		InputStream props =
				ClassLoader.getSystemResourceAsStream("portal.properties");
		InputSource inputSource = new InputSource(props);

		XMLReader parser = new PortalPropertiesParser();

		SAXSource saxSource = new SAXSource(parser, inputSource);

		TransformerFactory factory = TransformerFactory.newInstance();

		InputStream xsl = ClassLoader.getSystemResourceAsStream(
				"com/liferay/portal/tools/dependencies/properties-html.xsl");
		StreamSource xslSource = new StreamSource(xsl);

		Transformer transformer = factory.newTransformer(xslSource);

		StreamResult result = new StreamResult("properties.html");

		transformer.transform(saxSource, result);

		xsl.close();
		props.close();
	}

	public ContentHandler getContentHandler() {
		return _contentHandler;
	}

	public DTDHandler getDTDHandler() {
		return null;
	}

	public EntityResolver getEntityResolver() {
		return null;
	}

	public ErrorHandler getErrorHandler() {
		return null;
	}

	public boolean getFeature(String s) {
		return false;
	}

	public Object getProperty(String s) {
		return null;
	}

	public void parse(InputSource source) throws IOException, SAXException {
		InputStream is = source.getByteStream();

		byte[] bytes = StreamUtil.readBytes(is);

		String content = new String(bytes, StringPool.UTF8);

		_parse(content);
	}

	public void parse(String uri) {
	}

	public void setContentHandler(ContentHandler handler) {
		_contentHandler = handler;
	}

	public void setDTDHandler(DTDHandler d) {
	}

	public void setEntityResolver(EntityResolver e) {
	}

	public void setErrorHandler(ErrorHandler handler) {
	}

	public void setFeature(String s, boolean b) {
	}

	public void setProperty(String s, Object o) {
	}

	private void _attributeAdd(
			AttributesImpl attributes, String key, String value) {
		attributes.addAttribute(_namespaceURI, key, key, "", value);
	}

	private void _createProperty
			(String propertyTagName, Attributes attrs,
			 PropertyData propertyData) throws SAXException {

		_tagStart(propertyTagName, attrs);

		_tag("name", propertyData.name);
		_tag("anchor", propertyData.anchor);
		_tag("description", propertyData.description);
		_tag("value", propertyData.value);

		for (String alternativeValue : propertyData.alternativeValues) {
			_tag("value", alternativeValue, "alt", "true");
		}

		_tagEnd(propertyTagName);
	}

	private void _createSection(Section section) throws SAXException {

		_tagStart("section");

		_tag("title", section.title);
		_tag("anchor", section.anchor);

		_tagStart("content");

		for (PropertyData propertyData : section.propertyDataList) {
			AttributesImpl attrs = new AttributesImpl();

			_attributeAdd(attrs, "hidden",
					Boolean.toString(propertyData.hidden));

			_attributeAdd(attrs, "group",
					Integer.toString(propertyData.group));

			if (propertyData.groupStart) {
				_attributeAdd(attrs, "prefix", propertyData.prefix);
			} else {
				_attributeAdd(attrs, "prefix", "");
			}

			String propertyTagName = "property";

			if (!propertyData.groupStart && propertyData.group > 0) {
				propertyTagName += "-group";
			}

			_createProperty(propertyTagName, attrs, propertyData);

			// duplicate property to property-group if group is started
			if (propertyData.groupStart && propertyData.group > 0) {

				propertyTagName += "-group";

				_createProperty(propertyTagName, attrs, propertyData);
			}
		}

		_tagEnd("content");

		_tagEnd("section");
	}

	private String _makePrefix(String name, int minLeft, int deepRight) {

		int ndx = 0;
		while (minLeft > 0) {
			ndx = name.indexOf('.', ndx);
			if (ndx == -1) {
				break;
			}

			ndx++;
			minLeft--;
		}

		if (ndx == -1) {
			return "";
		}

		String prefix;
		if (ndx > 0) {
			ndx--;

			prefix = name.substring(0, ndx);
			name = name.substring(ndx);
		} else {
			prefix = "";
		}


		while (deepRight > 0) {
			ndx = name.lastIndexOf('.');
			if (ndx == -1) {
				return prefix;
			}
			name = name.substring(0, ndx);
			deepRight--;
		}
		return prefix + name;
	}
	
	private void _parse(String p) throws SAXException {

		_contentHandler.startDocument();

		_tagStart("params");
		_tag("fullToc", String.valueOf(PARAM_FULL_TOC));
		_tagEnd("params");

		_tagStart("properties");

		int ndx = 0;
		while(true) {
			ndx = p.indexOf("## ", ndx);

			if (ndx == -1) {
				break;
			}

			Section section = new Section();

			int titleEndNdx = p.indexOf('\n', ndx);

			section.title = p.substring(ndx + 3, titleEndNdx).trim();
			section.anchor = nameToAnchor(section.title);

			titleEndNdx += 5;

			int sectionEndNdx = p.indexOf("##", titleEndNdx);

			if (sectionEndNdx == -1) {
				sectionEndNdx = p.length();
			}

			String content = p.substring(titleEndNdx, sectionEndNdx);

			section.propertyDataList = _parseContent(content);
			
			_resolveGroups(section.propertyDataList);
			
			_createSection(section);

			ndx = sectionEndNdx;
		}

		_tagEnd("properties");

		_contentHandler.endDocument();
	}

	private List<PropertyData> _parseContent(String content) {

		List<PropertyData> propertyDataList = new ArrayList<PropertyData>();

		String[] lines = StringUtil.splitc(content, '\n');

		PropertyData propertyData = new PropertyData();

		boolean hidden;

		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			line = line.trim();

			hidden = false;

			if (StringUtil.startsWithChar(line, '#')) {
				// comments

				if ((line.length() >= 2) && (line.charAt(1) != ' ')) {
					line = line.substring(1);
					hidden = true;
				}
				else {
					propertyData.description += line.substring(1) + '\n';
					continue;
				}
			}

			// values
			int equalNdx = line.indexOf('=');

			if (equalNdx == -1) {
				continue;
			}

			propertyData.name = line.substring(0, equalNdx);
			propertyData.anchor = nameToAnchor(propertyData.name + '+' + i);
			propertyData.hidden = hidden;
			propertyData.prefix =_makePrefix(
					propertyData.name, PARAM_MIN_LEFT, PARAM_DEEP_RIGHT);

			String value = line.substring(equalNdx + 1);
			while (value.endsWith("\\")) {
				value = value.substring(0, value.length() - 1);
				i++;
				value += lines[i].trim();
			}


			propertyData.value = value;

			// check previous property for alternative values
			if (propertyData.description.length() == 0) {

				if (propertyDataList.isEmpty() == false) {

					PropertyData previousData =
							propertyDataList.get(propertyDataList.size() - 1);

					if (previousData.name.equals(propertyData.name)) {

						if (previousData.value.equals(propertyData.value)) {
							propertyData = new PropertyData();
							continue;
						}

						previousData.alternativeValues.add(propertyData.value);
						propertyData = new PropertyData();
						continue;
					}
				}
			}

			propertyDataList.add(propertyData);
			propertyData = new PropertyData();

		}

	    return propertyDataList;

	}

	private void _resolveGroups(
			List<PropertyData> propertyDataList) {

		int groupCount = 0;
		boolean newGroup = true;

		for (int i = 1; i < propertyDataList.size(); i++) {
			PropertyData prev = propertyDataList.get(i - 1);
			PropertyData curr = propertyDataList.get(i);

			if (
					prev.hidden == curr.hidden &&
					prev.alternativeValues.isEmpty() &&
					curr.alternativeValues.isEmpty() &&
					prev.prefix.equals(curr.prefix) &&
					(curr.description.length() == 0)
					) {

				// group founded

				if (newGroup == true) {
					groupCount++;
					prev.group = groupCount;
					prev.groupStart = true;
					newGroup = false;
				}
				curr.group = prev.group;
			} else {
				newGroup = true;
			}

		}
		
	}

	private void _tag(String tagName, String text) throws SAXException {
		_tagStart(tagName);
		_tagText(text);
		_tagEnd(tagName);
	}

	private void _tag(String tagName, String text, String key, String value)
			throws SAXException {

		AttributesImpl attributes = new AttributesImpl();

		_attributeAdd(attributes, key, value);

		_tagStart(tagName, attributes);
		_tagText(text);
		_tagEnd(tagName);
	}

	private void _tagEnd(String tagName) throws SAXException {

		_contentHandler.endElement(_namespaceURI, tagName, tagName);

	}

	private void _tagStart(String tagName) throws SAXException {

		_contentHandler.startElement(
				_namespaceURI, tagName, tagName, _attributes);

	}

	private void _tagStart(String tagName, Attributes attributes)
			throws SAXException {

		_contentHandler.startElement(
				_namespaceURI, tagName, tagName, attributes);

	}

	private void _tagText(String text) throws SAXException {

		_contentHandler.characters(text.toCharArray(), 0, text.length());
	}

	private String nameToAnchor(String name) {
		return StringUtil.replaceChars(
				name, " ".toCharArray(), "+".toCharArray());
	}

	private static class PropertyData {
		public String name = "";
		public String anchor = "";
		public String prefix = "";
		public String value = "";
		public String description = "";
		public boolean hidden;
		public int group;
		public boolean groupStart;
		public List<String> alternativeValues = new ArrayList<String>();
	}

	private static class Section {

		public String title;

		public String anchor;

		public List<PropertyData> propertyDataList;

	}

	private AttributesImpl _attributes = new AttributesImpl();

	private ContentHandler _contentHandler;

	private String _namespaceURI = "";

}
