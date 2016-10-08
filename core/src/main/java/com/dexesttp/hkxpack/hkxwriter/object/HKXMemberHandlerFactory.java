package com.dexesttp.hkxpack.hkxwriter.object;

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

/**
 * Creates the relevant {@link HKXMemberHandler} from a {@link HKXType}.
 * @see #create(HKXType, long)
 */
public class HKXMemberHandlerFactory {
	private final transient ByteBuffer outFile;
	private final transient HKXEnumResolver enumResolver;
	private final transient List<DataInternal> data1List;
	private final transient List<PointerObject> data2List;
	private final transient List<HKXMemberCallback> memberCallbacks;

	/**
	 * Creates a {@link HKXMemberHandlerFactory}.
	 * @param outFile the {@link ByteBuffer} to write into.
	 * @param enumResolver the {@link HKXEnumResolver} to use to resolve enums.
	 * @param data1List the list of {@link DataInternal} to fill while solving an array or a string.
	 * @param data2List the list of {@link DataExternal} to fill while solving pointers.
	 */
	public HKXMemberHandlerFactory(final ByteBuffer outFile, final HKXEnumResolver enumResolver,
			final List<DataInternal> data1List, final List<PointerObject> data2List,
			final List<HKXMemberCallback> memberCallbacks) {
		this.outFile = outFile;
		this.enumResolver = enumResolver;
		this.data1List = data1List;
		this.data2List = data2List;
		this.memberCallbacks = memberCallbacks;
	}
	
	/**
	 * Clones the factory, but changes the memberCallback queue.
	 * @param memberCallbacks then new {@link HKXMemberCallback} list to use.
	 * @return the cloned {@link HKXMemberHandlerFactory}.
	 */
	public HKXMemberHandlerFactory clone(final List<HKXMemberCallback> memberCallbacks) {
		return new HKXMemberHandlerFactory(outFile, enumResolver,
				data1List, data2List,
				memberCallbacks);
	}

	/**
	 * Creates the {@link HKXMemberHandler} relevant to the given {@link HKXMemberTemplate}.
	 * @param memberTemplate the {@link HKXMemberTemplate} to base the {@link HKXMemberHandler} on.
	 * @return the relevant {@link HKXMemberHandler}.
	 */
	public HKXMemberHandler create(final HKXType vtype, final long offset) {
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
	 * @deprecated {@link ByteBuffer} usage no longer allows nor requires this step
	 */
	public void close() {
		// Deprecated
	}
}
