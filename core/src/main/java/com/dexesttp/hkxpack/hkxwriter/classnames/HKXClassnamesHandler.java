package com.dexesttp.hkxpack.hkxwriter.classnames;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.hkx.classnames.ClassnamesData;
import com.dexesttp.hkxpack.resources.byteutils.ByteUtils;

/**
 * Creates a {@link ClassnamesData} from a {@link HKXFile}.
 */
public class HKXClassnamesHandler {
	private static final long HK_CLASS_ID = 0x33D42383;
	private static final long HK_CLASS_MEMBER_ID = 0xB0EFA719;
	private static final long HK_CLASS_ENUM_ID = 0x8A3609CF;
	private static final long HK_CLASS_ENUM_ITEM_ID = 0xCE6F8A6C;

	/**
	 * Creates a {@link ClassnamesData} instance from the given {@link HKXFile}.
	 * @param file the {@link HKXFile} to extract data from.
	 * @return the relevant {@link ClassnamesData}.
	 */
	public ClassnamesData getClassnames(final HKXFile file) {
		ClassnamesData data = new ClassnamesData();
		data.put(5, "hkClass", ByteUtils.fromULong(HK_CLASS_ID, 4));
		data.put(18, "hkClassMember", ByteUtils.fromULong(HK_CLASS_MEMBER_ID, 4));
		data.put(37, "hkClassEnum", ByteUtils.fromULong(HK_CLASS_ENUM_ID, 4));
		data.put(54, "hkClassEnumItem", ByteUtils.fromULong(HK_CLASS_ENUM_ITEM_ID, 4));
		int i = 75;
		for(HKXObject object : file.getContentCollection()) {
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
