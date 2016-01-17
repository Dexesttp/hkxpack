package com.dexesttp.hkxpack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.dexesttp.hkxpack.hkx.handler.HKXReader;
import com.dexesttp.hkxpack.hkx.handler.HKXReaderFactory;
import com.dexesttp.hkxpack.resources.exceptions.UnconnectedHKXException;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.resources.exceptions.UnresolvedMemberException;

public class Main {
	/**
	 * Main entry point.
	 */
	public void exec(String fileName) {
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
		}
	}
}
