package com.dexesttp.hkxpack.resources;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Credit : Eric Bruno
 * http://www.drdobbs.com/jvm/easy-dom-parsing-in-java/231002580
 */
public final class DOMUtils {

	private DOMUtils() {
		// NO OP
	}

	/**
	 * Retrieves thhe first node from a {@link NodeList}.
	 * 
	 * @param tagName the tag name of the node to retrieve
	 * @param nodes   the node list
	 * @return the relevant {@link Node}.
	 */
	public static Node getNode(final String tagName, final NodeList nodes) {
		for (int x = 0; x < nodes.getLength(); x++) {
			Node node = nodes.item(x);
			if (node.getNodeName().equalsIgnoreCase(tagName)) {
				return node;
			}
		}
		return null;
	}

	/**
	 * Retrieves the value of a given Node, or an empty String.
	 * 
	 * @param node the Node to analyze
	 * @return the node's value, or "".
	 */
	public static String getNodeValue(final Node node) {
		NodeList childNodes = node.getChildNodes();
		for (int x = 0; x < childNodes.getLength(); x++) {
			Node data = childNodes.item(x);
			if (data.getNodeType() == Node.TEXT_NODE) {
				return data.getNodeValue();
			}
		}
		return "";
	}

	/**
	 * Retrieves the value of a {@link Node} in an {@link NodeList}.
	 * 
	 * @param tagName the tag name of the {@link Node} to retrieve
	 * @param nodes   the {@link NodeList}.
	 * @return the first relevant {@link Node}'s value, or "" if it has no value.
	 */
	public static String getNodeValue(final String tagName, final NodeList nodes) {
		for (int x = 0; x < nodes.getLength(); x++) {
			Node node = nodes.item(x);
			if (node.getNodeName().equalsIgnoreCase(tagName)) {
				NodeList childNodes = node.getChildNodes();
				for (int y = 0; y < childNodes.getLength(); y++) {
					Node data = childNodes.item(y);
					if (data.getNodeType() == Node.TEXT_NODE) {
						return data.getNodeValue();
					}
				}
			}
		}
		return "";
	}

	/**
	 * Retrieves a {@link Node} attribute, or "" if there's no value.
	 * 
	 * @param attrName the name of the attribute to retrieve.
	 * @param node     the {@link Node} to retrieve from
	 * @return the attribute value, or "".
	 */
	public static String getNodeAttr(final String attrName, final Node node) {
		NamedNodeMap attrs = node.getAttributes();
		for (int y = 0; y < attrs.getLength(); y++) {
			Node attr = attrs.item(y);
			if (attr.getNodeName().equalsIgnoreCase(attrName)) {
				return attr.getNodeValue();
			}
		}
		return "";
	}

	/**
	 * Retrieves a {@link Node} attribute froma {@link NodeList}.
	 * 
	 * @param tagName  the tagName of the node to retrieve
	 * @param attrName the attribute name to retrieve
	 * @param nodes    the {@link NodeList} to retrieve from.
	 * @return the relevant attribute, or "".
	 */
	public static String getNodeAttr(final String tagName, final String attrName, final NodeList nodes) {
		for (int x = 0; x < nodes.getLength(); x++) {
			Node node = nodes.item(x);
			if (node.getNodeName().equalsIgnoreCase(tagName)) {
				NodeList childNodes = node.getChildNodes();
				for (int y = 0; y < childNodes.getLength(); y++) {
					Node data = childNodes.item(y);
					if (data.getNodeType() == Node.ATTRIBUTE_NODE && data.getNodeName().equalsIgnoreCase(attrName)) {
						return data.getNodeValue();
					}
				}
			}
		}
		return "";
	}
}
