package com.dexesttp.hkxpack.hkxwriter;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.hkx.HKXUtils;
import com.dexesttp.hkxpack.hkx.classnames.ClassnamesData;
import com.dexesttp.hkxpack.hkx.data.DataExternal;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkx.header.SectionData;
import com.dexesttp.hkxpack.hkxwriter.utils.PointerObject;
import com.dexesttp.hkxpack.hkxwriter.utils.PointerResolver;

/**
 * Handles the different components of the Data section.
 * <p>
 * This uses {@link HKXPointersHandler} and {@link HKXObjectHandler} as its main components.
 */
class HKXDataHandler {
	private final transient ByteBuffer outFile;
	private final transient ClassnamesData cnameData;
	private final transient HKXEnumResolver enumResolver;
	private final transient List<DataInternal> data1queue;
	private final transient List<PointerObject> data2queue;
	private final transient List<DataExternal> data3queue;

	/**
	 * Create a {@link HKXDataHandler} associated with the given output {@link ByteBuffer} as well as the given {@link HKXEnumResolver}.
	 * @param outFile the {@link ByteBuffer} to write data to.
	 * @param classnamesData the {@link ClassnamesData}.
	 * @param enumResolver the {@link HKXEnumResolver} to resolve enums with.
	 */
	HKXDataHandler(final ByteBuffer outFile, final ClassnamesData classnamesData, final HKXEnumResolver enumResolver) {
		this.outFile = outFile;
		this.cnameData = classnamesData;
		this.enumResolver = enumResolver;
		this.data1queue = new ArrayList<>();
		this.data2queue = new ArrayList<>();
		this.data3queue = new ArrayList<>();
	}

	/**
	 * Fill this {@link HKXDataHandler}'s {@link ByteBuffer} section 'data' with the given {@link HKXFile}'s contents.
	 * @param data the {@link SectionData} describing at least the data offset.
	 * @param file the {@link HKXFile} to write data from.
	 * @param resolver the PointerResolver to resolve objects with.
	 * @return the position of the byte just after the end of the Data section
	 */
	long fillFile(final SectionData data, final HKXFile file, final PointerResolver resolver) {
		long currentPos = data.offset;
		HKXObjectHandler objectHandler = new HKXObjectHandler(outFile, cnameData, data, enumResolver, data1queue, data2queue, data3queue, resolver);
		for(HKXObject object : file.getContentCollection()) {
			currentPos = objectHandler.handle(object, currentPos);
			currentPos = HKXUtils.snapLine(currentPos);
		}
		return currentPos;
	}

	/**
	 * Fill the file {@link ByteBuffer} with the intended Data pointers, and store the offsets in the given {@link SectionData}.
	 * @param data the section data to store the offsets into.
	 */
	void fillPointers(final SectionData data, final PointerResolver resolver) {
		HKXPointersHandler handler = new HKXPointersHandler(outFile, data);
		List<DataExternal> data2resolved = new ArrayList<>();
		for(DataInternal internal : data1queue) {
			internal.from -= data.offset;
			internal.to -= data.offset;
		}
		for(PointerObject ptr : data2queue) {
			resolver.resolve(ptr).ifPresent((pointerData) -> {
				pointerData.from -= data.offset;
				pointerData.to -= data.offset;
				data2resolved.add(pointerData);
			});
		}
		handler.write(data1queue, data2resolved, data3queue);
	}
}
