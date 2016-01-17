package com.dexesttp.hkxpack.hkx.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import org.w3c.dom.Document;

import com.dexesttp.hkxpack.hkx.classes.ClassFlagAssociator;
import com.dexesttp.hkxpack.hkx.classes.ClassMapper;
import com.dexesttp.hkxpack.hkx.reader.DataReader;
import com.dexesttp.hkxpack.hkx.reader.InternalLinkReader;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.resources.exceptions.UnresolvedMemberException;
import com.dexesttp.hkxpack.xml.classxml.definition.ClassXML;

public interface HKXHandler {
	public IHeader getHeader() throws UninitializedHKXException, IOException;

	public ClassMapper getMapper() throws FileNotFoundException, UninitializedHKXException, IOException;

	public ClassFlagAssociator getAssociator() throws IOException, UninitializedHKXException;

	public LinkedList<ClassXML> getInstanceList() throws IOException, UninitializedHKXException;

	public DataReader getDataReader() throws UninitializedHKXException, IOException;

	public InternalLinkReader getInternalLinkReader() throws UninitializedHKXException, IOException;

	// TODO create superinterface for these five guys.
	public void connect(File file);
	
	public void init() throws UninitializedHKXException, FileNotFoundException;

	public void resolveData() throws UninitializedHKXException, IOException, UnresolvedMemberException;

	public Document getDocument();

	public void close() throws IOException;

}
