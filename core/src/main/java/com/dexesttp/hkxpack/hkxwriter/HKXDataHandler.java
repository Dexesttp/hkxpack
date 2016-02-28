package com.dexesttp.hkxpack.hkxwriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
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
	private final File outFile;
	private final ClassnamesData cnameData;
	private final HKXEnumResolver enumResolver;
	private final List<DataInternal> data1queue;
	private final List<PointerObject> data2queue;
	private final List<DataExternal> data3queue;

	/**
	 * Create a {@link HKXDataHandler} assocaited with the given output {@link File} as well as the given {@link HKXEnumResolver}.
	 * @param outFile the {@link File} to write data to.
	 * @param classnamesData the {@link ClassnamesData}.
	 * @param enumResolver the {@link HKXEnumResolver} to resolve enums with.
	 */
	HKXDataHandler(File outFile, ClassnamesData classnamesData, HKXEnumResolver enumResolver) {
		this.outFile = outFile;
		this.cnameData = classnamesData;
		this.enumResolver = enumResolver;
		this.data1queue = new ArrayList<>();
		this.data2queue = new ArrayList<>();
		this.data3queue = new ArrayList<>();
	}

	/**
	 * Fill this {@link HKXDataHandler}'s {@link File} section 'data' with the given {@link HKXFile}'s contents.
	 * @param data the {@link SectionData} describing at least the data offset.
	 * @param file the {@link HKXFile} to write data from.
	 * @param resolver the PointerResolver to resolve objects with.
	 * @return the position of the byte just after the end of the Data section
	 * @throws IOException if there was a problem writing to this {@link HKXDataHandler}'s {@link File}.
	 */
	long fillFile(SectionData data, HKXFile file, PointerResolver resolver) throws IOException {
		long currentPos = data.offset;
		HKXObjectHandler objectHandler = new HKXObjectHandler(outFile, cnameData, data, enumResolver, data1queue, data2queue, data3queue, resolver);
		for(HKXObject object : file.content()) {
			currentPos = objectHandler.handle(object, currentPos);
			currentPos = snap(currentPos);
		}
		return currentPos;
	}

	/**
	 * Fill the file with the intended Data pointers, and store the offsets in the given {@link SectionData}.
	 * @param data the section data to store the offsets into.
	 * @throws IOException if there was a problem writing data to the file.
	 */
	void fillPointers(SectionData data, PointerResolver resolver) throws IOException {
		HKXPointersHandler handler = new HKXPointersHandler(outFile, data);
		// TODO resolve data2;
		List<DataExternal> data2resolved = new ArrayList<>();
		for(DataInternal internal : data1queue) {
			internal.from -= data.offset;
			internal.to -= data.offset;
		}
		for(PointerObject ptr : data2queue) {
			DataExternal resolved = resolver.resolve(ptr);
			if(resolved != null) {
				resolved.from -= data.offset;
				resolved.to -= data.offset;
				data2resolved.add(resolved);
			}
		}
		handler.write(data1queue, data2resolved, data3queue);
	}

	private long snap(long currentPos) {
		if(currentPos % 0x10 == 0)
			return currentPos;
		return (1 + currentPos / 0x10) * 0x10;
	}
}
