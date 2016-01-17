package com.dexesttp.hkxpack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.dexesttp.hkxpack.hkx.handler.HKXHandler;
import com.dexesttp.hkxpack.hkx.handler.HKXHandlerImpl;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;

public class Main {
	/**
	 * Main entry point.
	 */
	public void exec(String fileName) {
		try {
			// Check if requested file exists.
			File file = new File(fileName);
			// Create HKX Handler
			HKXHandler handler = new HKXHandlerImpl();
			// Connect the handler to the file.
			handler.connect(file);
			// Initialize the handler.
			handler.init();
			handler.readClassNames();
			// WTF resoltion worked ?!?!?
			handler.resolveData3();
			handler.resolveData2();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UninitializedHKXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
