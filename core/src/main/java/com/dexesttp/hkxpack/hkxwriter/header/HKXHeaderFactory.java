package com.dexesttp.hkxpack.hkxwriter.header;

import java.util.ArrayList;
import java.util.List;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.hkx.header.HeaderData;

/**
 * Creates a {@link HeaderData} object from the given {@link HKXFile}.
 */
public class HKXHeaderFactory {
	private List<String> animClassesList = new ArrayList<>();

	/**
	 * Creates a {@link HKXHeaderFactory}
	 */
	public HKXHeaderFactory() {
		animClassesList.add("hkaAnimationContainer");
		animClassesList.add("hclClothData");
	}
	
	/**
	 * Creates a {@link HeaderData} from a {@link HKXFile}.
	 * @param file the {@link HKXFile} to get the header from.
	 * @return the relevant {@link HeaderData}.
	 */
	public HeaderData create(HKXFile file) {
		boolean isAnim = false;
		for(HKXObject object : file.getContentCollection()) {
			if(animClassesList.contains(object.getDescriptor().getName()))
					isAnim = true;
		}
		
		HeaderData header = new HeaderData();
		header.version = file.getClassVersion();
		header.versionName = file.getContentsVersion();
		header.paddingAfter = isAnim ? 0x10 : 0x00;
		
		return header;
	}
}
