package com.dexesttp.hkxpack.hkxwriter.object.callbacks;

import java.util.List;

import com.dexesttp.hkxpack.hkx.data.DataInternal;

public class HKXBaseArrayMemberCallback implements HKXMemberCallback {
	private final HKXArrayMemberCallback callbackProcessor;
	private final List<DataInternal> data1;
	private final DataInternal arrData;

	public HKXBaseArrayMemberCallback(HKXArrayMemberCallback callbackProcessor, List<DataInternal> data1, DataInternal arrData) {
		this.callbackProcessor = callbackProcessor;
		this.data1 = data1;
		this.arrData = arrData;
	}

	@Override
	public long process(List<HKXMemberCallback> memberCallbacks, long position) {
		arrData.to = position;
		data1.add(arrData);
		return callbackProcessor.process(memberCallbacks, position);
	}

}
