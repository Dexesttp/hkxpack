package com.dexesttp.hkxpack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import com.dexesttp.hkxpack.hkx.classnames.ClassnamesData;
import com.dexesttp.hkxpack.hkx.classnames.ClassnamesInteface;
import com.dexesttp.hkxpack.hkx.header.HeaderData;
import com.dexesttp.hkxpack.hkx.header.HeaderInterface;
import com.dexesttp.hkxpack.hkx.header.SectionData;
import com.dexesttp.hkxpack.hkx.header.SectionInterface;

public class Main {
	/**
	 * Main entry point.
	 * @param outputFile 
	 */
	public void exec(String fileName, String outputFile) {
		try {
			// Read file
			File file = new File(fileName);
			// Read header
			HeaderInterface headInt = new HeaderInterface();
			headInt.connect(file);
			HeaderData header = headInt.extract();
			headInt.close();
			
			// Read header sections.
			SectionInterface sectInt = new SectionInterface();
			sectInt.connect(file, header);
			// See documentation to explain why this.
			SectionData classnamesHead = sectInt.extract(0);
			SectionData dataHead = sectInt.extract(2);
			
			//Read classnames
			ClassnamesInteface cnamesInt = new ClassnamesInteface();
			cnamesInt.connect(file, classnamesHead);
			ClassnamesData data = cnamesInt.extract();

			System.out.println(classnamesHead.name);
			
			// Output result
	        TransformerFactory transformerFactory =
	                 TransformerFactory.newInstance();
            Transformer transformer =
	                 transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        	transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			
			if(outputFile == "")
				new StreamResult(System.out);
			else
				new StreamResult(new File(outputFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
