package com.dexesttp.hkxpack.hkxwriter;

import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.hkx.classnames.ClassnamesData;
import com.dexesttp.hkxpack.hkx.classnames.ClassnamesInterface;
import com.dexesttp.hkxpack.hkx.exceptions.UnsupportedVersionError;
import com.dexesttp.hkxpack.hkx.header.HeaderData;
import com.dexesttp.hkxpack.hkx.header.HeaderInterface;
import com.dexesttp.hkxpack.hkx.header.SectionData;
import com.dexesttp.hkxpack.hkx.header.SectionInterface;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;

/**
 * Handles all connections to the {@link ByteBuffer} for {@link HeaderData} and
 * {@link SectionData} objects.
 */
class HKXWriterConnector {
	private final transient ByteBuffer file;

	/**
	 * Creates a {@link HKXWriterConnector} linked to the given {@link ByteBuffer}.
	 * 
	 * @param outputFile the {@link ByteBuffer} to link this connector to.
	 */
	HKXWriterConnector(final ByteBuffer outputFile) {
		this.file = outputFile;
	}

	/**
	 * Cleans the file.
	 * 
	 * @deprecated {@link ByteBuffer} usage no longer allows nor requires this step
	 */
	void clean() {
		// Deprecated
	}

	/**
	 * Writes the given {@link HeaderData} to this {@link HKXWriterConnector}'s
	 * {@link ByteBuffer}
	 * 
	 * @param data the {@link HeaderData} to write.
	 * @throws UnsupportedVersionError if the given {@link HeaderData} have an
	 *                                 unsupported version.
	 */
	void writeHeader(final HeaderData data) throws UnsupportedVersionError {
		HeaderInterface headerConnector = new HeaderInterface();
		headerConnector.connect(file);
		headerConnector.compress(data);
	}

	/**
	 * Writes the given {@link SectionData} as the sectionID's section header of
	 * this {@link HKXWriterConnector}'s {@link ByteBuffer}.
	 * 
	 * @param header    the {@link HeaderData} to seek offsets from.
	 * @param sectionID the section position to write.
	 * @param section   the {@link SectionData} to write to the file.
	 */
	void writeSection(final HeaderData header, final int sectionID, final SectionData section) {
		SectionInterface sectionConnector = new SectionInterface();
		sectionConnector.connect(file, header);
		sectionConnector.compress(section, sectionID);
	}

	/**
	 * Writes the given {@link ClassnamesData} object to this
	 * {@link HKXReaderConnector}'s {@link ByteBuffer}.
	 * 
	 * @param classnames the {@link SectionData} related to the
	 *                   {@link ClassnamesData}. It must be initialized at least for
	 *                   its offset.
	 * @param data       the {@link ClassnamesData} to write.
	 * @return the position of the byte just after the end of the ClassnamesData
	 *         section.
	 */
	public long writeClassnames(final SectionData classnames, final ClassnamesData data) {
		ClassnamesInterface classnamesConnector = new ClassnamesInterface();
		classnamesConnector.connect(file, classnames);
		return classnamesConnector.compress(data);
	}
}
