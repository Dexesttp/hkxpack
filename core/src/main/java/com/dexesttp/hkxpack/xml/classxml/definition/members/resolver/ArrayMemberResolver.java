package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.hkx.data.Data1Interface;
import com.dexesttp.hkxpack.hkx.data.Data2Interface;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.structs.DataInterface;
import com.dexesttp.hkxpack.resources.ByteUtils;
import com.dexesttp.hkxpack.xml.classxml.definition.members.MemberResolver;

public enum ArrayMemberResolver implements BaseMemberResolver {
	TYPE_ARRAY(16),
	TYPE_SIMPLEARRAY(16),
	TYPE_INPLACEARRAY(16),
	TYPE_RELARRAY(16);
	
	private final int size;
	private BaseMemberResolver content;

	private ArrayMemberResolver(int size) {
		this.size = size;
	}

	@Override
	public void setExtraData(String vsubtype, String ctype, String etype) {
		this.content =(BaseMemberResolver) MemberResolver.getEnum(vsubtype);
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public Node getData(Document document, byte[] toRead, DataInterface data, Data1Interface data1, Data2Interface data2) throws IOException, InvalidPositionException {
		byte[] lengthArray = {toRead[8], toRead[9], toRead[10], toRead[11]};
		int length = ByteUtils.getInt(lengthArray);
		if(length > 0) {
			DataInternal dataChunk = data1.readNext();
			long arrPos = dataChunk.to;
			for(int i = 0; i < length; i++) {
				content.getData(document, toRead, data, data1, data2);
			}
		}
		return null;
	}
}
