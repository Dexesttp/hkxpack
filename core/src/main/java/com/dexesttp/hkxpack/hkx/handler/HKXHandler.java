package com.dexesttp.hkxpack.hkx.handler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import com.dexesttp.hkxpack.hkx.classes.ClassFlagAssociator;
import com.dexesttp.hkxpack.hkx.classes.ClassMapper;
import com.dexesttp.hkxpack.hkx.classes.PointerResolver;
import com.dexesttp.hkxpack.hkx.reader.DataReader;
import com.dexesttp.hkxpack.hkx.reader.InternalLinkReader;
import com.dexesttp.hkxpack.hkx.reader.TripleLinkReader;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.xml.classxml.definition.ClassXML;

public interface HKXHandler extends HKXReader {
	public IHeader getHeader() throws UninitializedHKXException, IOException;

	public ClassMapper getMapper() throws FileNotFoundException, UninitializedHKXException, IOException;

	public ClassFlagAssociator getAssociator() throws IOException, UninitializedHKXException;

	public LinkedList<ClassXML> getInstanceList() throws IOException, UninitializedHKXException;

	public DataReader getDataReader() throws UninitializedHKXException, IOException;

	public TripleLinkReader getExternalLinkReader() throws UninitializedHKXException, IOException;

	public InternalLinkReader getInternalLinkReader() throws UninitializedHKXException, IOException;

	public PointerResolver getPtrResolver();

}
