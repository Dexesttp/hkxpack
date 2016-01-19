package com.dexesttp.hkxpack.hkx.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.dexesttp.hkxpack.commons.parser.ConstantReader;
import com.dexesttp.hkxpack.hkx.definition.ClassName;
import com.dexesttp.hkxpack.resources.ByteUtils;

public class ClassNamesReader extends ConstantReader<ClassName[]> {
	@Override
	protected ClassName[] readData() throws IOException {
		final long maxPos = position + size;
		List<ClassName> cnameList = new ArrayList<ClassName>();
		while(file.getFilePointer() < maxPos) {
			ClassName cname = new ClassName();
			file.read(cname.classCode);
			if(file.getFilePointer() > maxPos)
				break;
			if(file.readByte() == -1)
				break;
			cname.position = file.getFilePointer();
			cname.className = ByteUtils.readString(file);
			cnameList.add(cname);
		}
		return cnameList.toArray(new ClassName[1]);
	}

}
