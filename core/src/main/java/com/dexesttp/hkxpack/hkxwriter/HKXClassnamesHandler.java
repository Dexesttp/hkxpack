package com.dexesttp.hkxpack.hkxwriter;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.hkx.classnames.ClassnamesData;
import com.dexesttp.hkxpack.resources.ByteUtils;

class HKXClassnamesHandler {
	private static final long hkClassID = 0x33D42383;
	private static final long hkClassMemberID = 0xB0EFA719;
	private static final long hkClassEnumID = 0x8A3609CF;
	private static final long hkClassEnumItemID = 0xCE6F8A6C;
	ClassnamesData getClassnames(HKXFile file) {
		ClassnamesData data = new ClassnamesData();
		data.put(5, "hkClass", ByteUtils.fromLong(hkClassID, 4));
		data.put(18, "hkClassMember", ByteUtils.fromLong(hkClassMemberID, 4));
		data.put(37, "hkClassEnum", ByteUtils.fromLong(hkClassEnumID, 4));
		data.put(54, "hkClassEnumItem", ByteUtils.fromLong(hkClassEnumItemID, 4));
		int i = 75;
		for(HKXObject object : file.content()) {
			if(!data.containsClass(object.getDescriptor().getName())) {
				data.put(i,
						object.getDescriptor().getName(),
						ByteUtils.fromLong(object.getDescriptor().getSignature(), 4));
				i += object.getDescriptor().getName().length() + 0x06;
			}
		}
		return data;
	}
}
