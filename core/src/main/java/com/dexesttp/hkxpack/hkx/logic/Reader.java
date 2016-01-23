package com.dexesttp.hkxpack.hkx.logic;

import java.io.File;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.hkx.classnames.ClassnamesData;
import com.dexesttp.hkxpack.hkx.classnames.ClassnamesInteface;
import com.dexesttp.hkxpack.hkx.data.Data1Interface;
import com.dexesttp.hkxpack.hkx.data.Data2Interface;
import com.dexesttp.hkxpack.hkx.data.Data3Interface;
import com.dexesttp.hkxpack.hkx.data.DataExternal;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.header.HeaderData;
import com.dexesttp.hkxpack.hkx.header.HeaderInterface;
import com.dexesttp.hkxpack.hkx.header.SectionData;
import com.dexesttp.hkxpack.hkx.header.SectionInterface;
import com.dexesttp.hkxpack.hkx.structs.DataInterface;
import com.dexesttp.hkxpack.hkx.structs.Struct;
import com.dexesttp.hkxpack.resources.PointerNameGiver;
import com.dexesttp.hkxpack.xml.classxml.ClassXMLList;
import com.dexesttp.hkxpack.xml.classxml.definition.classes.ReadableClass;
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
		
		// Read header
		HeaderInterface headInt = new HeaderInterface();
		headInt.connect(file);
		HeaderData header = headInt.extract();
		headInt.close();
		
		// Get the TagXML document
		Document document = new TagXMLInitializer().initialize(header.version, header.versionName);
		Element root = document.createElement("hksection");
		root.setAttribute("name", "__data__");
		document.getChildNodes().item(0).appendChild(root);
		
		// Read header sections.
		SectionInterface sectInt = new SectionInterface();
		sectInt.connect(file, header);
		// See documentation to explain why this.
		SectionData classnamesHead = sectInt.extract(0);
		SectionData dataHead = sectInt.extract(2);
		sectInt.close();
		
		//Read classnames
		ClassnamesInteface cnamesInt = new ClassnamesInteface();
		cnamesInt.connect(file, classnamesHead);
		ClassnamesData classConverter = cnamesInt.extract();
		cnamesInt.close();
		
		// Resolve classnames
		classList.importClasses();
		classList.resolve();
		
		// Read data.
		// Connect the interfaces
		Data1Interface data1 = new Data1Interface();
		data1.connect(file, dataHead);
		Data2Interface data2 = new Data2Interface();
		data2.connect(file, dataHead);
		Data3Interface data3 = new Data3Interface();
		data3.connect(file, dataHead);
		DataInterface data = new DataInterface();
		data.connect(file, dataHead);
		// Read the data, class by class.
		int pos = 0;
		try {
			while(true) {
				// Get the next data3 class.
				DataExternal currentClass = data3.read(pos++);
				// Resolve the class.
				String className = classConverter.get(currentClass.to).name;
				ReadableClass actualClass = classList.getReadableClass(className);
				// Add a name to the class
				String newName = PointerNameGiver.getInstance().getName(currentClass.from);
				// Read the struct.
				Struct currentStruct = actualClass.getStruct();
				data.read(currentClass.from, currentStruct);
				// Resolve the struct to a Node using data/data1/data2.
				Node node = actualClass.resolve(document, newName, currentStruct, data, data1, data2);
				root.appendChild(node);
			}
		} catch (InvalidPositionException e) {
			if(!e.getSection().equals("DATA_3"))
				throw e;
		}
		return document;
	}
}
