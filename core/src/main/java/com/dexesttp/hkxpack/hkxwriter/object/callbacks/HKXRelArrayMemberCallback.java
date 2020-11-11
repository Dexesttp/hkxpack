package com.dexesttp.hkxpack.hkxwriter.object.callbacks;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.List;

import com.dexesttp.hkxpack.resources.byteutils.ByteUtils;

/**
 * Handles the {@link HKXMemberCallback} for a Rel (relative)
 * {@link HKXArrayMember}.
 */
public class HKXRelArrayMemberCallback implements HKXMemberCallback {
	private final transient HKXArrayMemberCallback callbackProcessor;
	private final transient ByteBuffer outFile;
	private final transient long classPos;
	private final transient long argPos;

	/**
	 * Create a {@link HKXRelArrayMemberCallback}.
	 * 
	 * @param callbackProcessor the {@link HKXArrayMemberCallback} to use for each
	 *                          array member.
	 * @param outFile           the {@link ByteBuffer} to write this array's
	 *                          position to.
	 * @param classPos          the position of the array's class.
	 * @param argPos            the position of the RelArray's argument from the
	 *                          beginning of the class.
	 */
	public HKXRelArrayMemberCallback(final HKXArrayMemberCallback callbackProcessor, final ByteBuffer outFile,
			final long classPos, final long argPos) {
		this.callbackProcessor = callbackProcessor;
		this.outFile = outFile;
		this.classPos = classPos;
		this.argPos = argPos;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public long process(final List<HKXMemberCallback> memberCallbacks, final long position) {
		byte[] offset = ByteUtils.fromULong(position - classPos, 2);
		((Buffer) outFile).position((int) (classPos + argPos + 2));
		outFile.put(offset);
		return callbackProcessor.process(memberCallbacks, position);
	}

}
