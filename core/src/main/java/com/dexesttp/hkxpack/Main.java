package com.dexesttp.hkxpack;

import java.io.File;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

public class Main {
	/**
	 * Main entry point.
	 * @param outputFile 
	 */
	public void exec(String fileName, String outputFile) {
		try {
			// Check if requested file exists.
			File file = new File(fileName);
			
			// Output result
	        TransformerFactory transformerFactory =
	                 TransformerFactory.newInstance();
            Transformer transformer =
	                 transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        	transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			
			StreamResult outResult;
			if(outputFile == "")
				outResult = new StreamResult(System.out);
			else
				outResult = new StreamResult(new File(outputFile));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
}
