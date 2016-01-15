package com.dexesttp.hkxpack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.dexesttp.hkxpack.hkx.definition.ClassName;
import com.dexesttp.hkxpack.hkx.definition.Header;
import com.dexesttp.hkxpack.hkx.definition.HeaderComponent;
import com.dexesttp.hkxpack.hkx.reader.ClassNamesReader;
import com.dexesttp.hkxpack.hkx.reader.HeaderReader;
import com.dexesttp.hkxpack.resources.ByteUtils;
import com.dexesttp.hkxpack.resources.FileUtils;
import com.dexesttp.hkxpack.xml.classxml.ClassXMLList;
import com.dexesttp.hkxpack.xml.classxml.definition.ClassXML;

public class Main {
	// Temporary main, will be encapsuled in a view later.
	public static void main(String[] args) {
		Main main = new Main();
		String fileName = "D:\\Documents\\SANDBOX\\FO4\\BloatflyRootBehavior.hkx";
		FileUtils.initFolder();
		main.exec(fileName);
	}
	
	/**
	 * Main entry point.
	 */
	public void exec(String fileName) {
		// Retrieve classXML instance
		ClassXMLList classXMLList = ClassXMLList.getInstance();
		try {
			// Check if requested file exists.
			File file = new File(fileName);
			// Read header
			HeaderReader reader = new HeaderReader();
			reader.connect(file, 0, 0);
			Header header = reader.read();
			// Read class list.
			HeaderComponent classHeader = header.components[0];
			ClassNamesReader cnameReader = new ClassNamesReader();
			cnameReader.connect(file, ByteUtils.getInt(classHeader.offset), ByteUtils.getInt(classHeader.part1));
			ClassName[] cNameList = cnameReader.read();
			// Retrieve class data.
			for(ClassName inst : cNameList) {
				System.out.println(inst.className);
				classXMLList.addClass(inst);
			}
			// Resolve class data
			classXMLList.import_classes();
			classXMLList.resolve();
			// WTF resoltion worked ?!?!?
			ClassXML resClass = classXMLList.get("hkbTimerModifier");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
