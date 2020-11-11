package com.dexesttp.hkxpack.hkx.classnames;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Map.Entry;

import com.dexesttp.hkxpack.hkx.header.HeaderData;
import com.dexesttp.hkxpack.hkx.header.SectionData;
import com.dexesttp.hkxpack.resources.byteutils.ByteUtils;

/**
 * Connects to a {@link ByteBuffer}, and allows easy retrieval and writing of
 * {@link ClassnamesData}.
 */
public class ClassnamesInterface {
	private static final char CLASSNAME_BREAKER = 0x09;
	private transient ByteBuffer file;
	private transient SectionData section;

	/**
	 * Connect to a given {@link ByteBuffer}, based on the given
	 * {@link SectionData}.
	 * 
	 * @param file   the {@link ByteBuffer} to connect to.
	 * @param header the {@link HeaderData} to base the search on.
	 */
	public void connect(final ByteBuffer file, final SectionData classnameSection) {
		this.file = file;
		this.section = classnameSection;
	}

	/**
	 * Compress the given classnames into the hkx file "__classname__" section. Note
	 * that it will fill the data with all required classnames & positions. Please
	 * use only negative position identifiers to fill the original dataset values.
	 * Otherwise, the method behaviour will be undefined.
	 * 
	 * @param data the data to find the classnames in.
	 * @return The position of the end of the dataset (absolute position from the
	 *         beginning of the file).
	 */
	public long compress(final ClassnamesData data) {
		((Buffer) file).position((int) section.offset);
		for (Entry<Long, Classname> classData : data.entrySet()) {
			file.put(classData.getValue().uuid);
			file.put((byte) 0x09);
			file.put(classData.getValue().name.getBytes());
			file.put((byte) 0x0);
		}
		// Fill the end with FFs and then return the pos.
		long pos = ((Buffer) file).position();
		long toDo = 0x10 - (pos % 0x10);
		pos += toDo;
		for (; toDo > 0; toDo--) {
			file.put((byte) -1);
		}
		return pos;
	}

	/**
	 * Extract all the classnames from a file's {@literal __classnames__} section.
	 * 
	 * @return the relevant, filled {@link ClassnamesData}.
	 */
	public ClassnamesData extract() {
		final long limit = section.offset + section.data1;
		ClassnamesData data = new ClassnamesData();
		byte[] idList = new byte[4];
		((Buffer) file).position((int) section.offset);
		while (((Buffer) file).position() < limit) {
			file.get(idList);
			if (file.get() != CLASSNAME_BREAKER) {
				break;
			}
			long position = ((Buffer) file).position();
			if (position > limit) {
				break;
			}
			String name = ByteUtils.readString(file);
			if (!name.isEmpty()) {
				data.put(position - section.offset, name, idList);
			}
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
