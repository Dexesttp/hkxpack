package com.dexesttp.hkxpack.hkxwriter.object.array;

import java.util.List;

import com.dexesttp.hkxpack.data.members.HKXPointerMember;
import com.dexesttp.hkxpack.hkx.data.DataExternal;
import com.dexesttp.hkxpack.hkxwriter.utils.PointerObject;

/**
 * Handle a {@link HKXPointerMember}'s array contents by deferring the position and content value definition,
 * and deferring its inclusion into the {@link DataExternal} list.
 */
public class HKXArrayPointerMemberHandler {
	private final transient List<PointerObject> data2;
	private transient PointerObject data2Instance;

	/**
	 * Creates a {@link HKXArrayPointerMemberHandler}
	 * @param data2List the {@link DataExternal} list to write the Pointer resolver to
	 */
	public HKXArrayPointerMemberHandler(final List<PointerObject> data2List) {
		this.data2 = data2List;
	}

	/**
	 * Set this pointer handler's value.
	 * @param internalPointer the {@link HKXPointerMember} to set.
	 */
	public void setPointer(final HKXPointerMember internalPointer) {
		data2Instance = new PointerObject();
		data2Instance.to = internalPointer.get();
		data2.add(data2Instance);
	}

	/**
	 * Resolve the current pointer to a position.
	 * @param newPos
	 */
	public void resolve(final long newPos) {
		data2Instance.from = newPos;
	}
}
