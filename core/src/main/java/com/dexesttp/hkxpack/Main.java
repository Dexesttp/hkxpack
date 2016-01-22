package com.dexesttp.hkxpack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.logic.Reader;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NonResolvedClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NotKnownClassException;

public class Main {
	/**
	 * Main entry point.
	 * @param outputFile 
	 */
	public void exec(String fileName, String outputFile) {
		try {
			// Read file
			File file = new File(fileName);
			
			Reader reader = new Reader();
			reader.read(file);
			
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
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NonResolvedClassException e) {
			e.printStackTrace();
		} catch (NotKnownClassException e) {
			e.printStackTrace();
		} catch (InvalidPositionException e) {
			e.printStackTrace();
		}
	}
}
