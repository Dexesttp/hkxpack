package com.dexesttp.hkxpack.hkx.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.dexesttp.hkxpack.hkx.classes.ClassFlagAssociator;
import com.dexesttp.hkxpack.hkx.classes.ClassMapper;
import com.dexesttp.hkxpack.hkx.reader.DataReader;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.xml.classxml.definition.ClassXML;

public interface HKXHandler {

	public void connect(File file);

	public void init() throws UninitializedHKXException, FileNotFoundException;

	public IHeader getHeader() throws UninitializedHKXException, IOException;

	public void close() throws IOException;

	public void readClassNames() throws FileNotFoundException, UninitializedHKXException, IOException;

	public void resolveData3() throws IOException, UninitializedHKXException;

	public ClassMapper getMapper() throws FileNotFoundException, UninitializedHKXException, IOException;

	public ClassFlagAssociator getAssociator() throws IOException, UninitializedHKXException;

	public void resolveData2() throws IOException, UninitializedHKXException;

	public List<ClassXML> getInstanceList() throws IOException, UninitializedHKXException;

	public void resolveData() throws UninitializedHKXException, IOException;

	public DataReader getDataReader() throws UninitializedHKXException, IOException;

}
