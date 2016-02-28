package com.dexesttp.hkxpack.hkxwriter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.hkx.classnames.ClassnamesData;
import com.dexesttp.hkxpack.hkx.data.DataExternal;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkx.header.SectionData;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;
import com.dexesttp.hkxpack.hkxwriter.object.HKXInternalObjectHandler;
import com.dexesttp.hkxpack.hkxwriter.object.HKXMemberCallback;
import com.dexesttp.hkxpack.hkxwriter.object.HKXMemberHandlerFactory;

public class HKXObjectHandler {
	private final HKXMemberHandlerFactory memberHandlerFactory;
	private final ClassnamesData cnameData;
	private final List<DataExternal> data3List;
	private final SectionData section;

	public HKXObjectHandler(File outFile, ClassnamesData classnamesData, SectionData dataSection,
			HKXEnumResolver enumResolver, List<DataInternal> data1List, List<DataExternal> data2List, List<DataExternal> data3List) {
		this.memberHandlerFactory = new HKXMemberHandlerFactory(outFile, enumResolver, data1List, data2List);
		this.cnameData = classnamesData;
		this.section = dataSection;
		this.data3List = data3List;
	}

	public long handle(HKXObject object, long currentPos) {
		// Add the object into data3.
		DataExternal classEntry = new DataExternal();
		classEntry.from = currentPos - section.offset;
		classEntry.section = 0x00;
		classEntry.to = cnameData.getPosition(object.getDescriptor().getName());
		data3List.add(classEntry);
		List<HKXMemberCallback> memberCallbacks = new ArrayList<HKXMemberCallback>();
		
		HKXInternalObjectHandler objectHandler = new HKXInternalObjectHandler(memberHandlerFactory, memberCallbacks);
		
		objectHandler.write(object, currentPos);
		
		// Resolve the member handlers.
		currentPos += MemberSizeResolver.getSize(object.getDescriptor());
		for(int i = 0; i < memberCallbacks.size(); i++) {
			HKXMemberCallback callback = memberCallbacks.get(i);
			currentPos += callback.process(memberCallbacks, currentPos);
		}
		return currentPos;
	}
}
