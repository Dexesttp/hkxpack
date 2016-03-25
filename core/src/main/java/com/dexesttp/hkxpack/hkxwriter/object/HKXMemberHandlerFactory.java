package com.dexesttp.hkxpack.hkxwriter.object;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.hkx.data.DataExternal;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkxwriter.object.array.HKXArrayMemberHandler;
import com.dexesttp.hkxpack.hkxwriter.object.array.HKXArrayPointerMemberHandler;
import com.dexesttp.hkxpack.hkxwriter.object.callbacks.HKXMemberCallback;
import com.dexesttp.hkxpack.hkxwriter.utils.PointerObject;

public class HKXMemberHandlerFactory {
	private final ByteBuffer outFile;
	private final HKXEnumResolver enumResolver;
	private final List<DataInternal> data1List;
	private final List<PointerObject> data2List;
	private List<HKXMemberCallback> memberCallbacks;

	/**
	 * Creates a {@link HKXMemberHandlerFactory}.
	 * @param outFile the {@link File} to write into.
	 * @param enumResolver the {@link HKXEnumResolver} to use to resolve enums.
	 * @param data1List the list of {@link DataInternal} to fill while solving an array or a string.
	 * @param data2List the list of {@link DataExternal} to fill while solving pointers.
	 * @throws IOException 
	 */
	public HKXMemberHandlerFactory(ByteBuffer outFile, HKXEnumResolver enumResolver,
			List<DataInternal> data1List, List<PointerObject> data2List,
			List<HKXMemberCallback> memberCallbacks) {
		this.outFile = outFile;
		this.enumResolver = enumResolver;
		this.data1List = data1List;
		this.data2List = data2List;
		this.memberCallbacks = memberCallbacks;
	}
	
	/**
	 * Clones the factory, but changes the memberCallback queue.
	 * @param memberCallbacks then new {@link HKXMemberCallback} list ot use.
	 * @return the cloned {@link HKXMemberHandlerFactory}.
	 */
	public HKXMemberHandlerFactory clone(List<HKXMemberCallback> memberCallbacks) {
		return new HKXMemberHandlerFactory(outFile, enumResolver,
				data1List, data2List,
				memberCallbacks);
	}

	/**
	 * Creates the {@link HKXMemberHandler} relevant to the given {@link HKXMemberTemplate}.
	 * @param memberTemplate the {@link HKXMemberTemplate} to base the {@link HKXMemberHandler} on.
	 * @return the relevant {@link HKXMemberHandler}.
	 */
	public HKXMemberHandler create(HKXType vtype, long offset) {
		switch(vtype.getFamily()) {
			case DIRECT:
			case COMPLEX:
				return new HKXDirectMemberHandler(outFile, offset);
			case ENUM:
				return new HKXEnumMemberHandler(outFile, offset, enumResolver);
			case STRING:
				return new HKXStringMemberHandler(outFile, offset, data1List);
			case POINTER:
				return new HKXPointerMemberHandler(offset, data2List);
			case OBJECT:
				return new HKXObjectMemberHandler(offset, this, memberCallbacks);
			case ARRAY:
				return new HKXArrayMemberHandler(outFile, offset, data1List, this);
			default:
				throw new IllegalArgumentException("Unknown type : " + vtype);
		}
	}
	
	/**
	 * Creates a APMH to handle pointer stacking at array initialization but the filling of the pointeronly at array writing.
	 * @return a brand new {@link HKXArrayPointerMemberHandler}.
	 */
	public HKXArrayPointerMemberHandler createAPMH() {
		return new HKXArrayPointerMemberHandler(data2List);
	}
	
	/**
	 * Close this {@link HKXMemberHandlerFactory}.
	 * @throws IOException if there was a problem closing the connection to the {@link File}.
	 */
	/*public void close() throws IOException {
		outFile.close();
	}*/
}
