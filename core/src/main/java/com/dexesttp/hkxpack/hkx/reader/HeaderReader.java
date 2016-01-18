package com.dexesttp.hkxpack.hkx.reader;

import java.io.IOException;

import com.dexesttp.hkxpack.commons.parser.ConstantReader;
import com.dexesttp.hkxpack.hkx.definition.Header;
import com.dexesttp.hkxpack.hkx.definition.HeaderComponent;
import com.dexesttp.hkxpack.resources.ByteUtils;

public class HeaderReader extends ConstantReader<Header> {
	@Override
	protected Header readData() throws IOException {
		Header res = new Header();
		file.skipBytes(res.magicCode.length);
		file.read(res.version);
		int versionNumber = ByteUtils.getInt(res.version);
		file.read(res.extras);
		file.read(res.constants);
		file.read(res.verName);
		file.read(res.padding);
		file.read(res.extras_new);
		file.read(res.padding_new);
		if(versionNumber > 8)
			file.skipBytes(ByteUtils.getInt(res.padding_new));
		for(HeaderComponent component : res.components) {
			file.read(component.sectionName);
			file.skipBytes(component.magic.length);
			file.read(component.offset);
			file.read(component.part1);
			file.read(component.part2);
			file.read(component.part3);
			file.read(component.part4);
			file.read(component.part5);
			file.read(component.part6);
			if(versionNumber > 8)
				file.skipBytes(16);
		}
		return res;
	}
}
