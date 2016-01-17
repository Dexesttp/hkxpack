package com.dexesttp.hkxpack.hkx.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.w3c.dom.Document;

import com.dexesttp.hkxpack.hkx.classes.ClassFlagAssociator;
import com.dexesttp.hkxpack.hkx.classes.ClassMapper;
import com.dexesttp.hkxpack.hkx.definition.Header;
import com.dexesttp.hkxpack.hkx.logic.ClassNameLogic;
import com.dexesttp.hkxpack.hkx.logic.Data1Logic;
import com.dexesttp.hkxpack.hkx.logic.Data2Logic;
import com.dexesttp.hkxpack.hkx.logic.Data3Logic;
import com.dexesttp.hkxpack.hkx.reader.ClassNamesReader;
import com.dexesttp.hkxpack.hkx.reader.DataReader;
import com.dexesttp.hkxpack.hkx.reader.HeaderReader;
import com.dexesttp.hkxpack.hkx.reader.InternalLinkReader;
import com.dexesttp.hkxpack.hkx.reader.TripleLinkReader;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.resources.exceptions.UnresolvedMemberException;
import com.dexesttp.hkxpack.xml.classxml.definition.ClassXML;

public class HKXHandlerImpl implements HKXHandler{
	// Data containers
	protected File file = null;
	protected Header header = null;
	protected ClassMapper classMapper = null;
	protected ClassFlagAssociator associator = null;
	protected List<ClassXML> instanceList = null;
	// Reader
	protected HeaderReader headerReader = null;
	protected ClassNamesReader cnameReader = null;
	protected TripleLinkReader data3reader = null;
	protected TripleLinkReader data2reader = null;
	protected InternalLinkReader data1reader = null;
	protected DataReader datareader = null;

	public HKXHandlerImpl() {
		this.headerReader = new HeaderReader();
		this.cnameReader = new ClassNamesReader();
		this.data3reader = new TripleLinkReader();
		this.data2reader = new TripleLinkReader();
		this.data1reader = new InternalLinkReader();
		this.datareader  = new DataReader();
	}
	
	@Override
	public void connect(File file) {
		this.file = file;
	}
	
	@Override
	public void init() throws UninitializedHKXException, FileNotFoundException {
		if(file == null)
			throw new UninitializedHKXException();
		headerReader.connect(file, 0, 0);
	}
	
	@Override
	public IHeader getHeader() throws UninitializedHKXException, IOException {
		if(header == null) {
			if(file == null)
				throw new UninitializedHKXException();
			header = headerReader.read();
		}
		return header;
	}
	
	@Override
	public void readClassNames() throws FileNotFoundException, UninitializedHKXException, IOException {
		cnameReader.connect(file, getHeader().getRegionOffset(0), getHeader().getRegionDataOffset(0, 1));
		classMapper = new ClassNameLogic(cnameReader).resolve();
	}
	
	@Override
	public ClassMapper getMapper() throws FileNotFoundException, UninitializedHKXException, IOException {
		if(classMapper == null)
			readClassNames();
		return classMapper;
	}
	
	@Override
	public void close() throws IOException {
		headerReader.close();
		file = null;
		header = null;
	}

	@Override
	public void resolveData3() throws IOException, UninitializedHKXException {
		final long begin = getHeader().getRegionOffset(2) + getHeader().getRegionDataOffset(2, 3);
		final long end = getHeader().getRegionOffset(2) + getHeader().getRegionDataOffset(2, 4);
		data3reader.connect(file, begin, end - begin);
		associator = new Data3Logic(data3reader).resolve(this);
	}
	
	@Override
	public ClassFlagAssociator getAssociator() throws IOException, UninitializedHKXException {
		if(associator == null)
			resolveData3();
		return associator;
	}
	
	@Override
	public void resolveData2() throws IOException, UninitializedHKXException {
		final long begin = getHeader().getRegionOffset(2) + getHeader().getRegionDataOffset(2, 2);
		final long end = getHeader().getRegionOffset(2) + getHeader().getRegionDataOffset(2, 3);
		data2reader.connect(file, begin, end - begin);
		instanceList = new Data2Logic(data2reader).resolve(this);
	}
	
	@Override
	public List<ClassXML> getInstanceList() throws IOException, UninitializedHKXException {
		if(instanceList == null)
			resolveData2();
		return instanceList;
	}
	
	@Override
	public void resolveData() throws UninitializedHKXException, IOException, UnresolvedMemberException {
		final long begin = getHeader().getRegionOffset(2) + getHeader().getRegionDataOffset(2, 1);
		final long end = getHeader().getRegionOffset(2) + getHeader().getRegionDataOffset(2, 2);
		data1reader.connect(file, begin, end - begin);
		new Data1Logic(data1reader).resolve(this);
	}
	
	@Override
	public DataReader getDataReader() throws UninitializedHKXException, IOException {
		if(!datareader.isConnected()) {
			final long begin = getHeader().getRegionOffset(2);
			final long length = getHeader().getRegionDataOffset(2, 1);
			datareader.connect(file, begin, length);
		}
		return datareader;
	}

	@Override
	public Document getDocument() {
		// TODO Auto-generated method stub
		return null;
	}
}
