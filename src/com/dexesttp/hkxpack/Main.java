package com.dexesttp.hkxpack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.dexesttp.hkxpack.hkx.handler.HKXReader;
import com.dexesttp.hkxpack.hkx.handler.HKXReaderFactory;
import com.dexesttp.hkxpack.resources.exceptions.UnconnectedHKXException;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.resources.exceptions.UnresolvedMemberException;

public class Main {
	/**
	 * Main entry point.
	 * @param outputFile 
	 */
	public void exec(String fileName, String outputFile) {
		try {
			// Check if requested file exists.
			File file = new File(fileName);
			// Create HKX Handler
			HKXReader reader = new HKXReaderFactory().build();
			// Connect the handler to the file.
			reader.connect(file);
			// Initialize the handler.
			reader.init();
			// WTF resoltion worked ?!?!?
			reader.resolveData();
			
			// Close the reader.
			reader.close();
			
			// Output result
	        TransformerFactory transformerFactory =
	                 TransformerFactory.newInstance();
            Transformer transformer =
	                 transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        	transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(reader.getDocument());
			
			StreamResult outResult;
			if(outputFile == "")
				outResult = new StreamResult(System.out);
			else
				outResult = new StreamResult(new File(outputFile));
	        transformer.transform(source, outResult);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnconnectedHKXException e) {
			e.printStackTrace();
		} catch (UninitializedHKXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnresolvedMemberException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
}
