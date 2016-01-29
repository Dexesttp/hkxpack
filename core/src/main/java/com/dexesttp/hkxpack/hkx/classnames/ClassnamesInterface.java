package com.dexesttp.hkxpack.hkx.classnames;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map.Entry;

import com.dexesttp.hkxpack.hkx.header.SectionData;
import com.dexesttp.hkxpack.resources.ByteUtils;

public class ClassnamesInterface {
	private RandomAccessFile file;
	private SectionData section;
	
	public void connect(File file, SectionData classnameSection) throws FileNotFoundException {
		this.file = new RandomAccessFile(file, "rw");
		this.section = classnameSection;
	}
	
	/**
	 * Compress the given classnames into the hkx file "__classname__" section.
	 * Note taht it will fill the data with all required classnames & positions.
	 * Please use only negative position identifiers to fill the original dataset values. Otherwise, the fi=unction comprtement will be indetermined.
	 * @param data the data to find the classnames in.
	 * @return The position of the end of the dataset (absolute position from the beginning of the file).
	 * @throws IOException
	 */
	public long compress(ClassnamesData data) throws IOException {
		file.seek(section.offset);
		for(Entry<Long, Classname> classData : data.entrySet()) {
			file.write(classData.getValue().uuid);
			file.writeByte(0x90);
			data.put(file.getFilePointer(), classData.getValue());
			file.writeChars(classData.getValue().name);
			file.writeByte(0x0);
		}
		// Fill the end with FFs and then return the pos.
		long pos = file.getFilePointer();
		long toDo = 0x10 - (pos % 0x10);
		pos += toDo;
		for(;toDo>0;toDo--)
			file.writeByte(-1);
		return pos;
	}
	
	public ClassnamesData extract() throws IOException {
		final long limit = section.offset + section.data1;
		ClassnamesData data = new ClassnamesData();
		byte[] idList = new byte[4];
		file.seek(section.offset);
		while(file.getFilePointer() < limit) {
			file.read(idList);
			if(file.readByte() != 0x09)
				break;
			long position = file.getFilePointer();
			if(position > limit)
				break;
			String name = ByteUtils.readString(file);
			if(!name.isEmpty())
				data.put(position - section.offset, name, idList);
		}
		return data;
	}

	public void close() throws IOException {
		file.close();
	}
}
