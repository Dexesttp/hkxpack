package com.dexesttp.hkxpack.hkxwriter.classnames;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.hkx.classnames.ClassnamesData;
import com.dexesttp.hkxpack.resources.byteutils.ByteUtils;

/**
 * Creates a {@link ClassnamesData} from a {@link HKXFile}.
 */
public class HKXClassnamesHandler {
	private static final long hkClassID = 0x33D42383;
	private static final long hkClassMemberID = 0xB0EFA719;
	private static final long hkClassEnumID = 0x8A3609CF;
	private static final long hkClassEnumItemID = 0xCE6F8A6C;

	/**
	 * Creates a {@link ClassnamesData} instance from the given {@link HKXFile}.
	 * @param file the {@link HKXFile} to extract data from.
	 * @return the relevant {@link ClassnamesData}.
	 */
	public ClassnamesData getClassnames(HKXFile file) {
		ClassnamesData data = new ClassnamesData();
		data.put(5, "hkClass", ByteUtils.fromULong(hkClassID, 4));
		data.put(18, "hkClassMember", ByteUtils.fromULong(hkClassMemberID, 4));
		data.put(37, "hkClassEnum", ByteUtils.fromULong(hkClassEnumID, 4));
		data.put(54, "hkClassEnumItem", ByteUtils.fromULong(hkClassEnumItemID, 4));
		int i = 75;
		for(HKXObject object : file.content()) {
			if(!data.containsClass(object.getDescriptor().getName())) {
				data.put(i,
						object.getDescriptor().getName(),
						ByteUtils.fromULong(object.getDescriptor().getSignature(), 4));
				i += object.getDescriptor().getName().length() + 0x06;
			}
		}
		return data;
	}
}
