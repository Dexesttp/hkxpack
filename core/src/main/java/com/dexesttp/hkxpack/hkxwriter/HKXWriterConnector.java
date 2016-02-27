package com.dexesttp.hkxpack.hkxwriter;

import java.io.File;
import java.io.IOException;

import com.dexesttp.hkxpack.hkx.classnames.ClassnamesData;
import com.dexesttp.hkxpack.hkx.classnames.ClassnamesInterface;
import com.dexesttp.hkxpack.hkx.exceptions.UnsupportedVersionError;
import com.dexesttp.hkxpack.hkx.header.HeaderData;
import com.dexesttp.hkxpack.hkx.header.HeaderInterface;
import com.dexesttp.hkxpack.hkx.header.SectionData;
import com.dexesttp.hkxpack.hkx.header.SectionInterface;

class HKXWriterConnector {

	private File file;

	HKXWriterConnector(File outputFile) {
		this.file = outputFile;
	}

	void writeHeader(HeaderData data) throws IOException, UnsupportedVersionError {
		HeaderInterface headerConnector = new HeaderInterface();
		headerConnector.connect(file);
		headerConnector.compress(data);
		headerConnector.close();
	}
	
	void writeSection(HeaderData header, int sectionID, SectionData section) throws IOException {
		SectionInterface sectionConnector = new SectionInterface();
		sectionConnector.connect(file, header);
		sectionConnector.compress(section, sectionID);
		sectionConnector.close();
	}

	public long writeClassnames(HeaderData header, SectionData classnames, ClassnamesData data) throws IOException {
		ClassnamesInterface classnamesConnector = new ClassnamesInterface();
		classnamesConnector.connect(file, classnames);
		long cnameEnd = classnamesConnector.compress(data);
		classnamesConnector.close();
		return cnameEnd;
	}
}
