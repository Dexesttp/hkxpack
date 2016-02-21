package com.dexesttp.hkxpack.hkx.header;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.dexesttp.hkxpack.hkx.exceptions.UnsupportedVersionError;
import com.dexesttp.hkxpack.hkx.header.internals.HeaderDescriptor;
import com.dexesttp.hkxpack.hkx.header.internals.versions.HeaderDescriptor_v11;
import com.dexesttp.hkxpack.resources.ByteUtils;

/**
 * Connects to a HKX {@link File} and allows direct access to the header contents.
 */
public class HeaderInterface {
	private RandomAccessFile file;

	/**
	 * Connect to a {@link File}
	 * @param file the file to connect to.
	 * @throws FileNotFoundException If the given file could not be found.
	 */
	public void connect(File file) throws FileNotFoundException {
		this.file = new RandomAccessFile(file, "rw");
	}

	/**
	 * Create a new header based on the given {@link HeaderData}
	 * @param data the {@link HeaderData} to retrieve the data from.
	 * @throws IOException if there was an erro while writing the data to the {@link File}
	 * @throws UnsupportedVersionError if the {@link HeaderData} contains a non-supported version
	 */
	public void compress(HeaderData data) throws IOException, UnsupportedVersionError {
		if(data.version == 11) {
			HeaderDescriptor_v11 descriptor = new HeaderDescriptor_v11();
			file.seek(0);
			file.write(descriptor.file_id);
			file.write(descriptor.version);
			file.write(descriptor.extras);
			file.write(descriptor.constants);
			file.write(descriptor.verName);
			file.write(descriptor.constants_2);
			file.write(descriptor.extras_v11);
			if(data.padding_after == 16) {
				file.write(descriptor.padding_v11);
				file.write(descriptor.padding);	
			} else {
				file.write(new byte[] {0, 0});
			}
		} else {
			throw new UnsupportedVersionError(data.versionName);
		}
	}

	/**
	 * Extract the {@link HeaderData} from the linked {@link File}
	 * @return the extracted {@link HeaderData}
	 * @throws IOException if there was a problem while reading the {@link File}
	 */
	public HeaderData extract() throws IOException {
		HeaderData data = new HeaderData();
		HeaderDescriptor descriptor = new HeaderDescriptor();
		file.seek(0);
		file.read(descriptor.file_id);
		file.read(descriptor.version);
		file.read(descriptor.extras);
		file.read(descriptor.constants);
		file.read(descriptor.verName);
		file.read(descriptor.constants_2);
		file.read(descriptor.extras_v11);
		file.read(descriptor.padding_v11);
		data.version = ByteUtils.getInt(descriptor.version);
		data.versionName = new String(descriptor.verName);
		if(data.version == 11)
			data.padding_after = ByteUtils.getLong(descriptor.padding_v11);
		else
			data.padding_after = 0;
		return data;
	}

	/**
	 * Close the connection with the {@link File}
	 * @throws IOException if there was a problem while closing the {@link File}
	 */
	public void close() throws IOException {
		file.close();
	}
}
