package com.dexesttp.hkxpack.hkx.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import org.w3c.dom.Document;

import com.dexesttp.hkxpack.hkx.classes.ClassFlagAssociator;
import com.dexesttp.hkxpack.hkx.classes.ClassMapper;
import com.dexesttp.hkxpack.hkx.classes.PointerResolver;
import com.dexesttp.hkxpack.hkx.definition.Header;
import com.dexesttp.hkxpack.hkx.logic.ClassNameLogic;
import com.dexesttp.hkxpack.hkx.logic.Data3Logic;
import com.dexesttp.hkxpack.hkx.logic.DataLogic;
import com.dexesttp.hkxpack.hkx.logic.InstanceListLogic;
import com.dexesttp.hkxpack.hkx.reader.ClassNamesReader;
import com.dexesttp.hkxpack.hkx.reader.DataReader;
import com.dexesttp.hkxpack.hkx.reader.HeaderReader;
import com.dexesttp.hkxpack.hkx.reader.InternalLinkReader;
import com.dexesttp.hkxpack.hkx.reader.TripleLinkReader;
import com.dexesttp.hkxpack.resources.exceptions.UnconnectedHKXException;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.resources.exceptions.UnresolvedMemberException;
import com.dexesttp.hkxpack.xml.classxml.definition.ClassXML;
import com.dexesttp.hkxpack.xml.tagxml.TagXMLInitializer;

public class HKXHandlerImpl implements HKXHandler{
	// Data containers
	protected File file = null;
	protected Header header = null;
	protected ClassMapper classMapper = null;
	protected ClassFlagAssociator associator = null;
	protected LinkedList<ClassXML> instanceList = null;
	protected PointerResolver resolver = null;
	// Output document
	protected Document document = null;
	// Readers
	protected HeaderReader headerReader = null;
	protected ClassNamesReader cnameReader = null;
	protected DataReader datareader = null;
	protected InternalLinkReader data1reader = null;
	protected TripleLinkReader data2reader = null;
	protected TripleLinkReader data3reader = null;

	public HKXHandlerImpl() {
		this.headerReader = new HeaderReader();
		this.cnameReader = new ClassNamesReader();
		this.data3reader = new TripleLinkReader();
		this.data2reader = new TripleLinkReader();
		this.data1reader = new InternalLinkReader();
		this.datareader  = new DataReader();
		this.resolver = new PointerResolver();
	}
	
	@Override
	public void connect(File file) throws FileNotFoundException, UnconnectedHKXException {
		this.file = file;
		if(file == null)
			throw new UnconnectedHKXException();
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
	public ClassMapper getMapper() throws FileNotFoundException, UninitializedHKXException, IOException {
		if(classMapper == null) {
			cnameReader.connect(file, getHeader().getRegionOffset(0), getHeader().getRegionDataOffset(0, 1));
			classMapper = new ClassNameLogic(cnameReader).resolve();
		}
		return classMapper;
	}
	
	@Override
	public ClassFlagAssociator getAssociator() throws IOException, UninitializedHKXException {
		if(associator == null) {
			final long begin = getHeader().getRegionOffset(2) + getHeader().getRegionDataOffset(2, 3);
			final long end = getHeader().getRegionOffset(2) + getHeader().getRegionDataOffset(2, 4);
			data3reader.connect(file, begin, end - begin);
			associator = new Data3Logic(data3reader).resolve(this);
		}
		return associator;
	}
	
	@Override
	public LinkedList<ClassXML> getInstanceList() throws IOException, UninitializedHKXException {
		if(instanceList == null) {
			instanceList = new InstanceListLogic().resolve(this);
		}
		return instanceList;
	}

	@Override
	public PointerResolver getPtrResolver() {
		return resolver;
	}

	@Override
	public InternalLinkReader getInternalLinkReader() throws UninitializedHKXException, IOException {
		if(!data1reader.isConnected()) {
			final long begin = getHeader().getRegionOffset(2) + getHeader().getRegionDataOffset(2, 1);
			final long end = getHeader().getRegionOffset(2) + getHeader().getRegionDataOffset(2, 2);
			data1reader.connect(file, begin, end - begin);
		}
		return data1reader;
	}

	@Override
	public TripleLinkReader getExternalLinkReader() throws UninitializedHKXException, IOException {
		if(!data2reader.isConnected()) {
			final long begin = getHeader().getRegionOffset(2) + getHeader().getRegionDataOffset(2, 2);
			final long end = getHeader().getRegionOffset(2) + getHeader().getRegionDataOffset(2, 3);
			data2reader.connect(file, begin, end - begin);
		}
		return data2reader;
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
	public void resolveData() throws UninitializedHKXException, IOException, UnresolvedMemberException {
		new DataLogic().resolve(this);
	}
	
	@Override
	public void close() throws IOException {
		headerReader.close();
		cnameReader.close();
		datareader.close();
		data1reader.close();
		data2reader.close();
		data3reader.close();
		file = null;
		header = null;
	}

	@Override
	public Document getDocument() throws IOException, UninitializedHKXException {
		if(document == null)
			document = new TagXMLInitializer().initialize(getHeader().getVersion(), getHeader().getVersionName());
		return document;
	}
}
