package com.dexesttp.hkxpack.hkx.logic;

import java.io.IOException;
import java.util.LinkedList;

import com.dexesttp.hkxpack.hkx.classes.ClassFlagAssociator;
import com.dexesttp.hkxpack.hkx.classes.ClassMapper;
import com.dexesttp.hkxpack.hkx.definition.TripleLink;
import com.dexesttp.hkxpack.hkx.handler.HKXHandler;
import com.dexesttp.hkxpack.hkx.reader.TripleLinkReader;
import com.dexesttp.hkxpack.resources.ByteUtils;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.xml.classxml.ClassXMLList;
import com.dexesttp.hkxpack.xml.classxml.definition.ClassXML;

public class Data2Logic {

	protected TripleLinkReader reader;

	public Data2Logic(TripleLinkReader data2reader) {
		this.reader = data2reader;
	}

	public LinkedList<ClassXML> resolve(HKXHandler handler) throws IOException, UninitializedHKXException {
		final ClassMapper mapper = handler.getMapper();
		final ClassFlagAssociator associator = handler.getAssociator();
		final long dataOffset = handler.getHeader().getRegionOffset(2);
		final LinkedList<ClassXML> instanceList = new LinkedList<>();
		TripleLink link;
		long flag, externalData, classPos;
		String name;
		while((link = reader.read()) != null) {
			externalData = ByteUtils.getInt(link.from) + dataOffset;
			flag = ByteUtils.getInt(link.to);
			// TODO remove debug
			System.out.println("Flag : " + flag);
			// TODO find something to do with this... thing ?
			System.out.println("External data : " + externalData);
			classPos = associator.getClass(flag);
			name = mapper.getName(classPos);
			instanceList.add(ClassXMLList.getInstance().get(name));
		}
		return instanceList;
	}
}
