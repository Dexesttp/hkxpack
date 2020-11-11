package com.dexesttp.hkxpack.hkxwriter;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.hkx.HKXUtils;
import com.dexesttp.hkxpack.hkx.classnames.ClassnamesData;
import com.dexesttp.hkxpack.hkx.data.DataExternal;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkx.header.SectionData;
import com.dexesttp.hkxpack.hkx.types.ObjectSizeResolver;
import com.dexesttp.hkxpack.hkxwriter.object.HKXInternalObjectHandler;
import com.dexesttp.hkxpack.hkxwriter.object.HKXMemberHandlerFactory;
import com.dexesttp.hkxpack.hkxwriter.object.callbacks.HKXMemberCallback;
import com.dexesttp.hkxpack.hkxwriter.utils.PointerObject;
import com.dexesttp.hkxpack.hkxwriter.utils.PointerResolver;

/**
 * Handles writing an {@link HKXObject} and its contents to a
 * {@link ByteBuffer}.
 */
public class HKXObjectHandler {
	private final transient ClassnamesData cnameData;
	private final transient SectionData section;
	private final transient PointerResolver resolver;
	private final transient ByteBuffer outFile;
	private final transient HKXEnumResolver enumResolver;
	private final transient List<DataInternal> data1List;
	private final transient List<PointerObject> data2List;
	private final transient List<DataExternal> data3List;

	/**
	 * Creates a general purpose {@link HKXObjectHandler}.
	 * 
	 * @param outFile        the {@link ByteBuffer} to output in.
	 * @param classnamesData the {@link ClassnamesData} to link classes positions
	 *                       to.
	 * @param dataSection    the data section header, to retrieve offsets from.
	 * @param enumResolver   the {@link HKXEnumResolver} to use.
	 * @param data1List      the data1 (internal) temporary values list.
	 * @param data2List      the data2 (external) temporary values list
	 * @param data3List      the data3 (root classes) temporary values list.
	 * @param resolver       the {@link PointerResolver} to use.
	 */
	public HKXObjectHandler(final ByteBuffer outFile, final ClassnamesData classnamesData,
			final SectionData dataSection, final HKXEnumResolver enumResolver, final List<DataInternal> data1List,
			final List<PointerObject> data2List, final List<DataExternal> data3List, final PointerResolver resolver) {
		this.outFile = outFile;
		this.enumResolver = enumResolver;
		this.cnameData = classnamesData;
		this.section = dataSection;
		this.data1List = data1List;
		this.data2List = data2List;
		this.data3List = data3List;
		this.resolver = resolver;
	}

	/**
	 * Handle object writing for the given {@link HKXObject}, at the given position.
	 * 
	 * @param object     the {@link HKXObject} to handle.
	 * @param currentPos the position to write the object at.
	 * @return the position at the end of the object.
	 */
	public long handle(final HKXObject object, final long currentPos) {
		// Add the object into data3 and the resolver
		DataExternal classEntry = new DataExternal();
		classEntry.from = currentPos - section.offset;
		classEntry.section = 0x00;
		classEntry.to = cnameData.getPosition(object.getDescriptor().getName());
		data3List.add(classEntry);
		resolver.add(object.getName(), currentPos);
		List<HKXMemberCallback> memberCallbacks = new ArrayList<HKXMemberCallback>();
		HKXMemberHandlerFactory memberHandlerFactory = new HKXMemberHandlerFactory(outFile, enumResolver, data1List,
				data2List, memberCallbacks);

		HKXInternalObjectHandler objectHandler = new HKXInternalObjectHandler(memberHandlerFactory, memberCallbacks);

		objectHandler.write(object, currentPos);

		// Resolve the member handlers.
		long nextPos = currentPos;
		nextPos += HKXUtils.snapLine(ObjectSizeResolver.getSize(object));
		while (!memberCallbacks.isEmpty()) {
			HKXMemberCallback callback = memberCallbacks.remove(0);
			nextPos += callback.process(memberCallbacks, nextPos);
		}
		return nextPos;
	}
}
