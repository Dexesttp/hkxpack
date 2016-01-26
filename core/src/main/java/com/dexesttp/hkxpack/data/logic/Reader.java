package com.dexesttp.hkxpack.data.logic;

import java.io.File;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.structures.StructureReader;
import com.dexesttp.hkxpack.hkx.HKXConnector;
import com.dexesttp.hkxpack.hkx.classnames.ClassnamesData;
import com.dexesttp.hkxpack.hkx.data.Data3Interface;
import com.dexesttp.hkxpack.hkx.data.DataExternal;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.header.HeaderData;
import com.dexesttp.hkxpack.resources.PointerNameGiver;
import com.dexesttp.hkxpack.xml.classxml.ClassXMLList;
import com.dexesttp.hkxpack.xml.classxml.definition.classes.ClassResolver;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NonImportedClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NonResolvedClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownEnumerationException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnsupportedCombinaisonException;
import com.dexesttp.hkxpack.xml.tagxml.TagXMLInitializer;

public class Reader {
	public Document read(File file) throws IOException, NonResolvedClassException, UnknownClassException, InvalidPositionException, UnsupportedCombinaisonException, NumberFormatException, UnknownEnumerationException, NonImportedClassException {
		// Get the ClassXMLList instance.
		ClassXMLList classList = ClassXMLList.getInstance();
		
		// Create HKXConnector compound
		HKXConnector connector = new HKXConnector(file);
		
		// Retrieve useful data and interfaces from the header
		HeaderData header = connector.header;
		ClassnamesData classConverter = connector.classnamesdata;
		Data3Interface data3 = connector.data3;
		
		// Get the TagXML document
		Document document = new TagXMLInitializer().initialize(header.version, header.versionName);
		Element root = document.createElement("hksection");
		root.setAttribute("name", "__data__");
		document.getChildNodes().item(0).appendChild(root);
		
		// Resolve classnames
		classList.importClasses();
		classList.resolve();
		
		int pos = 0;
		try {
			while(true) {
				// Get the next data3 class.
				DataExternal currentClass = data3.read(pos++);
				// Resolve the class.
				String className = classConverter.get(currentClass.to).name;
				ClassResolver actualClass = classList.getClassResolver(className);
				StructureReader reader = actualClass.getReader();
				// Add a name to the class
				String newName = PointerNameGiver.getInstance().getName(currentClass.from);
				// Read the class
				Node node = reader.read(currentClass.from, document, connector, newName);
				// Add the class as a child node.
				root.appendChild(node);
			}
		} catch (InvalidPositionException e) {
			if(!e.getSection().equals("DATA_3"))
				throw e;
		}
		return document;
	}
}
