package com.dexesttp.hkxpack.hkx.header;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.hkx.exceptions.UnsupportedVersionError;
import com.dexesttp.hkxpack.hkx.header.internals.HeaderDescriptor;
import com.dexesttp.hkxpack.hkx.header.internals.versions.HeaderDescriptor_v11;
import com.dexesttp.hkxpack.resources.byteutils.ByteUtils;

/**
 * Connects to a HKX {@link ByteBuffer} and allows direct access to the header contents.
 */
public class HeaderInterface {
	private static final long ONE_LINE_PADDING = 0x10;
	private transient ByteBuffer file;

	/**
	 * Connect to a {@link ByteBuffer}
	 * @param file the {@link ByteBuffer} to connect to.
	 */
	public void connect(final ByteBuffer file) {
		this.file = file;
	}

	/**
	 * Create a new header based on the given {@link HeaderData}
	 * @param data the {@link HeaderData} to retrieve the data from.
	 * @throws UnsupportedVersionError if the {@link HeaderData} contains a non-supported version
	 */
	public void compress(final HeaderData data) throws UnsupportedVersionError {
		if(data.version == HeaderDescriptor_v11.VERSION_11 ) {
			HeaderDescriptor_v11 descriptor = new HeaderDescriptor_v11();
			((Buffer)file).position(0);
			file.put(descriptor.fileID);
			file.put(descriptor.version);
			file.put(descriptor.extras);
			file.put(descriptor.constants);
			file.put(descriptor.verName);
			file.put(descriptor.constants2);
			file.put(descriptor.extras11);
			if(data.paddingAfter == ONE_LINE_PADDING ) {
				file.put(descriptor.padding11);
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
		((Buffer)file).position(0);
		file.get(descriptor.fileID);
		file.get(descriptor.version);
		file.get(descriptor.extras);
		file.get(descriptor.constants);
		file.get(descriptor.verName);
		file.get(descriptor.constants2);
		file.get(descriptor.extras11);
		file.get(descriptor.padding11);
		data.version = ByteUtils.getUInt(descriptor.version);
		data.versionName = new String(descriptor.verName);
		if(data.version == HeaderDescriptor_v11.VERSION_11) {
			data.paddingAfter = ByteUtils.getULong(descriptor.padding11);
		}
		else {
			data.paddingAfter = 0;
		}
		return data;
	}

	/**
	 * @deprecated {@link ByteBuffer} usage no longer allows nor requires this step
	 */
	public void close() {
		// Deprecated
	}
}
