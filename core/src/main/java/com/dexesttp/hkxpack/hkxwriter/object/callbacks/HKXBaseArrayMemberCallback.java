package com.dexesttp.hkxpack.hkxwriter.object.callbacks;

import java.util.List;

import com.dexesttp.hkxpack.hkx.data.DataInternal;

/**
 * The base array's callback.
 * @see HKXArrayMemberCallback
 */
public class HKXBaseArrayMemberCallback implements HKXMemberCallback {
	private final transient HKXArrayMemberCallback callbackProcessor;
	private final transient List<DataInternal> data1;
	private final transient DataInternal arrData;

	/**
	 * Create a {@link HKXBaseArrayMemberCallback}.
	 * @param callbackProcessor the {@link HKXArrayMemberCallback} to use for each array member.
	 * @param data1 the {@link DataInternal} list to fill the array's position into.
	 * @param arrData the {@link DataInternal} prepared with the array hook's position.
	 */
	public HKXBaseArrayMemberCallback(final HKXArrayMemberCallback callbackProcessor, final List<DataInternal> data1, final DataInternal arrData) {
		this.callbackProcessor = callbackProcessor;
		this.data1 = data1;
		this.arrData = arrData;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public long process(final List<HKXMemberCallback> memberCallbacks, final long position) {
		arrData.to = position;
		data1.add(arrData);
		return callbackProcessor.process(memberCallbacks, position);
	}

}
