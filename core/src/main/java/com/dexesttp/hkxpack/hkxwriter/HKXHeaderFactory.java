package com.dexesttp.hkxpack.hkxwriter;

import java.util.ArrayList;
import java.util.List;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.hkx.header.HeaderData;

/**
 * Creates a {@link HeaderData} object from the given {@link HKXFile}.
 */
class HKXHeaderFactory {
	private List<String> animClassesList = new ArrayList<>();

	HKXHeaderFactory() {
		animClassesList.add("hkaAnimationContainer");
	}
	
	HeaderData create(HKXFile file) {
		boolean isAnim = false;
		for(HKXObject object : file.content()) {
			if(animClassesList.contains(object.getDescriptor().getName()))
					isAnim = true;
		}
		
		HeaderData header = new HeaderData();
		header.version = file.getClassVersion();
		header.versionName = file.getContentsVersion();
		header.padding_after = isAnim ? 0x10 : 0x00;
		
		return header;
	}
}
