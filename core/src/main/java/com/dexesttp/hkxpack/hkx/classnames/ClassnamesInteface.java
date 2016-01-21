package com.dexesttp.hkxpack.hkx.classnames;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map.Entry;

import com.dexesttp.hkxpack.hkx.header.SectionData;
import com.dexesttp.hkxpack.resources.ByteUtils;

public class ClassnamesInteface {
	private RandomAccessFile file;
	private SectionData section;
	
	public void connect(File file, SectionData classnameSection) throws FileNotFoundException {
		this.file = new RandomAccessFile(file, "rw");
		this.section = classnameSection;
	}
	
	public long compress(ClassnamesData data) throws IOException {
		file.seek(section.offset);
		for(Entry<Integer, String> classData : data.entrySet()) {
			file.write(ByteUtils.fromInt(classData.getKey()));
			file.writeChar(0x90);
			file.writeChars(classData.getValue());
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
		ClassnamesData data = new ClassnamesData();
		byte[] idList = new byte[4];
		file.seek(section.offset);
		while(file.getFilePointer() < section.data1 + section.offset) {
			file.read(idList);
			int id = ByteUtils.getInt(idList);
			if(file.readByte() != 0x90)
				break;
			String name = ByteUtils.readString(file);
			if(!name.isEmpty())
				data.put(id, name);
		}
		return data;
	}

	public void close() throws IOException {
		file.close();
	}
}
