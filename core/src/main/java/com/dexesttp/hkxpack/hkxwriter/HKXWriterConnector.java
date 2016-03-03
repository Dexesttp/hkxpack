package com.dexesttp.hkxpack.hkxwriter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import com.dexesttp.hkxpack.hkx.classnames.ClassnamesData;
import com.dexesttp.hkxpack.hkx.classnames.ClassnamesInterface;
import com.dexesttp.hkxpack.hkx.exceptions.UnsupportedVersionError;
import com.dexesttp.hkxpack.hkx.header.HeaderData;
import com.dexesttp.hkxpack.hkx.header.HeaderInterface;
import com.dexesttp.hkxpack.hkx.header.SectionData;
import com.dexesttp.hkxpack.hkx.header.SectionInterface;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;

/**
 * Handles all connections to the {@link File} for {@link HeaderData} and {@link SectionData} objects.
 */
class HKXWriterConnector {
	private File file;

	/**
	 * Creates a {@link HKXWriterConnector} linked to the given {@link File}.
	 * @param outputFile the {@link File} to link this connector to.
	 */
	HKXWriterConnector(File outputFile) {
		this.file = outputFile;
	}
	
	/**
	 * Cleans the file.
	 * @throws IOException if there was a problem cleaning the file.
	 */
	void clean() throws IOException {
		PrintWriter writer = new PrintWriter(file);
		writer.close();
	}

	/**
	 * Writes the given {@link HeaderData} to this {@link HKXWriterConnector}'s {@link File}
	 * @param data the {@link HeaderData} to write.
	 * @throws IOException if there was an error while writing to the {@link File}.
	 * @throws UnsupportedVersionError if the given {@link HeaderData} have an unsupported version.
	 */
	void writeHeader(HeaderData data) throws IOException, UnsupportedVersionError {
		HeaderInterface headerConnector = new HeaderInterface();
		headerConnector.connect(file);
		headerConnector.compress(data);
		headerConnector.close();
	}

	/**
	 * Writes the given {@link SectionData} as the sectionID's section header of this {@link HKXWriterConnector}'s {@link File}.
	 * @param header the {@link HeaderData} to seek offsets from.
	 * @param sectionID the section position to write.
	 * @param section the {@link SectionData} to write to the file.
	 * @throws IOException if there was a problem while writing to the {@link File}.
	 */
	void writeSection(HeaderData header, int sectionID, SectionData section) throws IOException {
		SectionInterface sectionConnector = new SectionInterface();
		sectionConnector.connect(file, header);
		sectionConnector.compress(section, sectionID);
		sectionConnector.close();
	}

	/**
	 * Writes the given {@link ClassnamesData} object to this {@link HKXReaderConnector}'s {@link File}.
	 * @param classnames the {@link SectionData} related to the {@link ClassnamesData}. It must be initialized at least for its offset.
	 * @param data the {@link ClassnamesData} to write.
	 * @return the position of the byte just after the end of the ClassnamesData section.
	 * @throws IOException if there was a problem while writing to the {@link File}.
	 */
	public long writeClassnames(SectionData classnames, ClassnamesData data) throws IOException {
		ClassnamesInterface classnamesConnector = new ClassnamesInterface();
		classnamesConnector.connect(file, classnames);
		long cnameEnd = classnamesConnector.compress(data);
		classnamesConnector.close();
		return cnameEnd;
	}
}
