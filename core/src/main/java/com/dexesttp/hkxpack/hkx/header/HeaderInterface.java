package com.dexesttp.hkxpack.hkx.header;

import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.hkx.exceptions.UnsupportedVersionError;
import com.dexesttp.hkxpack.hkx.header.internals.HeaderDescriptor;
import com.dexesttp.hkxpack.hkx.header.internals.versions.HeaderDescriptor_v11;
import com.dexesttp.hkxpack.resources.ByteUtils;

/**
 * Connects to a HKX {@link ByteBuffer} and allows direct access to the header contents.
 */
public class HeaderInterface {
	private ByteBuffer file;

	/**
	 * Connect to a {@link ByteBuffer}
	 * @param file the {@link ByteBuffer} to connect to.
	 */
	public void connect(ByteBuffer file) {
		this.file = file;
	}

	/**
	 * Create a new header based on the given {@link HeaderData}
	 * @param data the {@link HeaderData} to retrieve the data from.
	 * @throws UnsupportedVersionError if the {@link HeaderData} contains a non-supported version
	 */
	public void compress(HeaderData data) throws UnsupportedVersionError {
		if(data.version == 11) {
			HeaderDescriptor_v11 descriptor = new HeaderDescriptor_v11();
			file.position(0);
			file.put(descriptor.file_id);
			file.put(descriptor.version);
			file.put(descriptor.extras);
			file.put(descriptor.constants);
			file.put(descriptor.verName);
			file.put(descriptor.constants_2);
			file.put(descriptor.extras_v11);
			if(data.padding_after == 16) {
				file.put(descriptor.padding_v11);
				file.put(descriptor.padding);	
			} else {
				file.put(new byte[] {0, 0});
			}
		} else {
			throw new UnsupportedVersionError(data.versionName);
		}
	}

	/**
	 * Extract the {@link HeaderData} from the linked {@link ByteBuffer}
	 * @return the extracted {@link HeaderData}
	 */
	public HeaderData extract() {
		HeaderData data = new HeaderData();
		HeaderDescriptor descriptor = new HeaderDescriptor();
		file.position(0);
		file.get(descriptor.file_id);
		file.get(descriptor.version);
		file.get(descriptor.extras);
		file.get(descriptor.constants);
		file.get(descriptor.verName);
		file.get(descriptor.constants_2);
		file.get(descriptor.extras_v11);
		file.get(descriptor.padding_v11);
		data.version = ByteUtils.getInt(descriptor.version);
		data.versionName = new String(descriptor.verName);
		if(data.version == 11)
			data.padding_after = ByteUtils.getLong(descriptor.padding_v11);
		else
			data.padding_after = 0;
		return data;
	}

	/**
	 * @deprecated {@link ByteBuffer} usage no longer allows or requires this step
	 */
	public void close() {
	}
}
