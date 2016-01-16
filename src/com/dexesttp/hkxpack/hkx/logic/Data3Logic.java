package com.dexesttp.hkxpack.hkx.logic;

import java.io.IOException;

import com.dexesttp.hkxpack.hkx.classes.ClassFlagAssociator;
import com.dexesttp.hkxpack.hkx.classes.ClassMapper;
import com.dexesttp.hkxpack.hkx.definition.TripleLink;
import com.dexesttp.hkxpack.hkx.handler.HKXHandler;
import com.dexesttp.hkxpack.hkx.reader.TripleLinkReader;
import com.dexesttp.hkxpack.resources.ByteUtils;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.xml.classxml.ClassXMLList;
import com.dexesttp.hkxpack.xml.classxml.definition.ClassXML;

public class Data3Logic {

	protected TripleLinkReader reader;

	public Data3Logic(TripleLinkReader data3reader) {
		this.reader = data3reader;
	}

	public ClassFlagAssociator resolve(HKXHandler handler) throws IOException, UninitializedHKXException {
		final long classOffset = handler.getHeader().getRegionOffset(0);
		final ClassMapper mapper = handler.getMapper();
		final ClassFlagAssociator associator = new ClassFlagAssociator();
		TripleLink link;
		long position, flag;
		String name;
		while((link = reader.read()) != null) {
			flag = ByteUtils.getInt(link.from);
			position = ByteUtils.getInt(link.to) + classOffset;
			name = mapper.getName(position);
			associator.add(flag, position);
			// TODO remove debug
			System.out.println("Val 1 : " + flag);
			System.out.println("Val 3 [Conv] : " + name);
			ClassXML classInstance = ClassXMLList.getInstance().get(name);
			classInstance.toString();
		}
		return associator;
	}

}
