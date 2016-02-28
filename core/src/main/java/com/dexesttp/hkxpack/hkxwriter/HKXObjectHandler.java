package com.dexesttp.hkxpack.hkxwriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import com.dexesttp.hkxpack.hkxwriter.utils.PointerObject;
import com.dexesttp.hkxpack.hkxwriter.utils.PointerResolver;

public class HKXObjectHandler {
	private final ClassnamesData cnameData;
	private final SectionData section;
	private final PointerResolver resolver;
	private final File outFile;
	private final HKXEnumResolver enumResolver;
	private final List<DataInternal> data1List;
	private final List<PointerObject> data2List;
	private final List<DataExternal> data3List;

	public HKXObjectHandler(File outFile, ClassnamesData classnamesData, SectionData dataSection,
			HKXEnumResolver enumResolver, List<DataInternal> data1List,
			List<PointerObject> data2List, List<DataExternal> data3List,
			PointerResolver resolver)
					throws FileNotFoundException {
		this.outFile = outFile;
		this.enumResolver = enumResolver;
		this.cnameData = classnamesData;
		this.section = dataSection;
		this.data1List = data1List;
		this.data2List = data2List;
		this.data3List = data3List;
		this.resolver = resolver;
	}

	public long handle(HKXObject object, long currentPos) throws IOException {
		// Add the object into data3 and the resolver
		DataExternal classEntry = new DataExternal();
		classEntry.from = currentPos - section.offset;
		classEntry.section = 0x00;
		classEntry.to = cnameData.getPosition(object.getDescriptor().getName());
		data3List.add(classEntry);
		resolver.add(object.getName(), currentPos);
		List<HKXMemberCallback> memberCallbacks = new ArrayList<HKXMemberCallback>();
		HKXMemberHandlerFactory memberHandlerFactory = new HKXMemberHandlerFactory(outFile, enumResolver, data1List, data2List, memberCallbacks);
		
		HKXInternalObjectHandler objectHandler = new HKXInternalObjectHandler(memberHandlerFactory, memberCallbacks);
		
		objectHandler.write(object, currentPos);
		
		// Resolve the member handlers.
		currentPos += MemberSizeResolver.getSize(object.getDescriptor());
		while(!memberCallbacks.isEmpty()) {
			HKXMemberCallback callback = memberCallbacks.remove(0);
			currentPos += callback.process(memberCallbacks, currentPos);
		}
		memberHandlerFactory.close();
		return currentPos;
	}
}
