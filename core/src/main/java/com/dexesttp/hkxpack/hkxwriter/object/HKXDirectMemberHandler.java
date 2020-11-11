package com.dexesttp.hkxpack.hkxwriter.object;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.hkx.types.MemberDataResolver;
import com.dexesttp.hkxpack.hkxwriter.object.callbacks.HKXMemberCallback;

/**
 * Handles a {@link HKXDirectMember} for writing to a HKX File.
 * @see MemberDataResolver#fromMember(HKXMember)
 */
public class HKXDirectMemberHandler implements HKXMemberHandler {
	private final transient ByteBuffer outFile;
	private final transient long memberOffset;

	/**
	 * Creates a {@link HKXDirectMemberHandler}.
	 * @param outFile the {@link ByteBuffer} to write to.
	 * @param memberOffset the member offset in its class.
	 */
	HKXDirectMemberHandler(final ByteBuffer outFile, final long memberOffset) {
		this.outFile = outFile;
		this.memberOffset = memberOffset;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXMemberCallback write(final HKXMember member, final long currentPos) {
		byte[] value = MemberDataResolver.fromMember(member);
		((Buffer)outFile).position((int) (currentPos + memberOffset));
		outFile.put(value);
		return (memberCallbacks, position) -> { return 0; };
	}
}
