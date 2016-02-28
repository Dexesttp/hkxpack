package com.dexesttp.hkxpack.hkxwriter.object;

import java.io.File;
import java.util.List;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.hkx.data.DataExternal;
import com.dexesttp.hkxpack.hkx.data.DataInternal;

public class HKXObjectHandler {
	private final File outFile;
	private final HKXEnumResolver enumResolver;
	private List<DataInternal> data1List;
	private List<DataExternal> data2List;
	private List<DataExternal> data3List;

	public HKXObjectHandler(File outFile, HKXEnumResolver enumResolver, List<DataInternal> data1List, List<DataExternal> data2List, List<DataExternal> data3List) {
		this.outFile = outFile;
		this.enumResolver = enumResolver;
		this.data1List = data1List;
		this.data2List = data2List;
		this.data3List = data3List;
	}

	public long handle(HKXObject object, long currentPos) {
		// TODO handle object
		return currentPos;
	}
}
