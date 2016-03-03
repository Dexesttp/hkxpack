package com.dexesttp.hkxpack.hkxwriter.object;

import java.util.List;

import com.dexesttp.hkxpack.data.members.HKXPointerMember;
import com.dexesttp.hkxpack.hkxwriter.utils.PointerObject;

public class HKXArrayPointerMemberHandler {
	private final List<PointerObject> data2;
	private PointerObject data2Instance;

	public HKXArrayPointerMemberHandler(List<PointerObject> data2List) {
		this.data2 = data2List;
	}

	public void setPointer(HKXPointerMember internalPointer) {
		data2Instance = new PointerObject();
		data2Instance.to = internalPointer.get();
		data2.add(data2Instance);
	}

	public void resolve(long newPos) {
		data2Instance.from = newPos;
	}

}
