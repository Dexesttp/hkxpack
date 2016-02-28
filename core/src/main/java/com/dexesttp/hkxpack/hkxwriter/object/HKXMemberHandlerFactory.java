package com.dexesttp.hkxpack.hkxwriter.object;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.hkx.data.DataExternal;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkxwriter.utils.PointerObject;

public class HKXMemberHandlerFactory {
	private final RandomAccessFile outFile;
	private final HKXEnumResolver enumResolver;
	private final List<DataInternal> data1List;
	private final List<PointerObject> data2List;

	/**
	 * Creates a {@link HKXMemberHandlerFactory}.
	 * @param outFile the {@link File} to write into.
	 * @param enumResolver the {@link HKXEnumResolver} to use to resolve enums.
	 * @param data1List the list of {@link DataInternal} to fill while solving an array or a string.
	 * @param data2List the list of {@link DataExternal} to fill while solving pointers.
	 * @throws FileNotFoundException if there was a problem opening a conenction to the given {@link File}.
	 */
	public HKXMemberHandlerFactory(File outFile, HKXEnumResolver enumResolver,
			List<DataInternal> data1List, List<PointerObject> data2List)
			throws FileNotFoundException {
		this.outFile = new RandomAccessFile(outFile, "rw");
		this.enumResolver = enumResolver;
		this.data1List = data1List;
		this.data2List = data2List;
	}

	/**
	 * Creates the {@link HKXMemberHandler} rekevant to the given {@link HKXMemberTemplate}.
	 * @param memberTemplate the {@link HKXMemberTemplate} to base the {@link HKXMemberHandler} on.
	 * @return the relevant {@link HKXMemberHandler}.
	 */
	public HKXMemberHandler create(HKXMemberTemplate memberTemplate) {
		switch(memberTemplate.vtype.getFamily()) {
			case DIRECT:
			case COMPLEX:
				return new HKXDirectMemberHandler(outFile, memberTemplate.offset);
			case ENUM:
				return new HKXEnumMemberHandler(outFile, memberTemplate.offset, enumResolver, memberTemplate.target);
			case POINTER:
				return new HKXPointerMemberHandler(memberTemplate.offset, data2List);
			case OBJECT:
				return new HKXObjectMemberHandler(memberTemplate.offset, this, data1List);
			default:
				// TODO once all known cases are handled, throw an error.
				return (member, currentPos) ->{
						return (memberCallbacks, position) -> {
								return 0;
							};
					};
		}
	}
	
	/**
	 * Close this {@link HKXMemberHandlerFactory}.
	 * @throws IOException if there was a problem closing the connection to the {@link File}.
	 */
	public void close() throws IOException {
		outFile.close();
	}
}
