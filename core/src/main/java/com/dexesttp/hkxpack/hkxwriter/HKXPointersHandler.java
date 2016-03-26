package com.dexesttp.hkxpack.hkxwriter;

import java.nio.ByteBuffer;
import java.util.List;

import com.dexesttp.hkxpack.hkx.data.Data1Interface;
import com.dexesttp.hkxpack.hkx.data.Data2Interface;
import com.dexesttp.hkxpack.hkx.data.Data3Interface;
import com.dexesttp.hkxpack.hkx.data.DataExternal;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkx.header.SectionData;

/**
 * Write pointers (stored internally as a list of {@link DataInternal} and {@link DataExternal}) into the given file {@link ByteBuffer}, at the described position.
 */
class HKXPointersHandler {
	private ByteBuffer outFile;
	private SectionData data;

	/**
	 * Create a {@link HKXPointersHandler}.
	 * @param outFile the {@link ByteBuffer} to write data to.
	 * @param data the {@link SectionData} describing at least the wanted offset and data1 position.
	 */
	HKXPointersHandler(ByteBuffer outFile, SectionData data) {
		this.outFile = outFile;
		this.data = data;
	}

	/**
	 * Write the pointers list to this handler's {@link ByteBuffer}.
	 * @param data1List the list of {@link DataInternal} to store into data1
	 * @param data2List the list of {@link DataExternal} to store into data2
	 * @param data3List the list of {@link DataExternal} to store into data3
	 */
	void write(List<DataInternal> data1List, List<DataExternal> data2List, List<DataExternal> data3List) {
		// handle data1
		Data1Interface connector1 = new Data1Interface();
		connector1.connect(outFile, data);
		long endPos = data.data1;
		int i = 0;
		for(DataInternal internal : data1List) {
			endPos = connector1.write(i++, internal);
		}
		endPos = fillBytes(endPos + data.offset) - data.offset;
		data.data2 = endPos;
		
		// Handle data2
		Data2Interface connector2 = new Data2Interface();
		connector2.connect(outFile, data);
		int j = 0;
		for(DataExternal pointer : data2List) {
			endPos = connector2.write(j++, pointer);
		}
		endPos = fillBytes(endPos + data.offset) - data.offset;
		data.data3 = endPos;
		
		// Handle data3
		Data3Interface connector3 = new Data3Interface();
		connector3.connect(outFile, data);
		int k = 0;
		for(DataExternal classLink : data3List) {
			endPos = connector3.write(k++, classLink);
		}
		endPos = fillBytes(endPos + data.offset) - data.offset;
		
		// Fill the section header.
		data.data4 = endPos;
		data.data5 = endPos;
		data.end = endPos;
	}

	private long fillBytes(long endPos) {
		if(endPos % 0x10 == 0)
			return endPos;
		long newEndPos = (1 + endPos / 0x10) * 0x10;
		outFile.position((int) endPos);
		for(; endPos < newEndPos; endPos++ ) {
			outFile.put((byte) 0xFF);
		}
		return newEndPos;
	}

}
