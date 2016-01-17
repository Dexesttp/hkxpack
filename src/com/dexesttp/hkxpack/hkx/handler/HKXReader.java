package com.dexesttp.hkxpack.hkx.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.w3c.dom.Document;

import com.dexesttp.hkxpack.resources.exceptions.UnconnectedHKXException;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.resources.exceptions.UnresolvedMemberException;

public interface HKXReader {

	// TODO create superinterface for these five guys.
	public void connect(File file) throws FileNotFoundException, UnconnectedHKXException;

	public void resolveData() throws UninitializedHKXException, IOException, UnresolvedMemberException;

	public Document getDocument() throws IOException, UninitializedHKXException;

	public void close() throws IOException;

}
