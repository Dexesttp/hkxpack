package com.dexesttp.hkxpack.hkx.logic;

import java.io.IOException;

import com.dexesttp.hkxpack.hkx.classes.ClassMapper;
import com.dexesttp.hkxpack.hkx.definition.TripleLink;
import com.dexesttp.hkxpack.hkx.handler.HKXHandler;
import com.dexesttp.hkxpack.hkx.reader.TripleLinkReader;
import com.dexesttp.hkxpack.resources.ByteUtils;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;

public class Data3Logic {

	private TripleLinkReader reader;

	public Data3Logic(TripleLinkReader data3reader) {
		this.reader = data3reader;
	}

	public void resolve(HKXHandler handler) throws IOException, UninitializedHKXException {
		final long classOffset = handler.getHeader().getRegionOffset(0);
		System.out.println(classOffset);
		TripleLink link;
		ClassMapper mapper = handler.getMapper();
		while((link = reader.read()) != null) {
			System.out.println("Val 1 : " + ByteUtils.getInt(link.from));
			System.out.println("Val 2 : " + ByteUtils.getInt(link.value));
			System.out.println("Val 3 : " + ByteUtils.getInt(link.to));
			System.out.println("Val 3 [Conv] : " + mapper.getName(ByteUtils.getInt(link.to) + classOffset));
		}
	}

}
