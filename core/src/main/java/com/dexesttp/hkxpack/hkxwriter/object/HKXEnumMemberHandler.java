package com.dexesttp.hkxpack.hkxwriter.object;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.data.members.HKXEnumMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;
import com.dexesttp.hkxpack.hkxwriter.object.callbacks.HKXMemberCallback;
import com.dexesttp.hkxpack.resources.byteutils.ByteUtils;

/**
 * Handles {@link HKXEnumMember} for writing them to a HKX File
 */
public class HKXEnumMemberHandler implements HKXMemberHandler {
	private final transient ByteBuffer outFile;
	private final transient long offset;
	private final transient HKXEnumResolver enumResolver;

	/**
	 * Creates a {@link HKXEnumMemberHandler}.
	 * 
	 * @param outFile      the output {@link ByteBuffer} to write to.
	 * @param offset       the offset of the memebr in the class.
	 * @param enumResolver the {@link HKXEnumResolver} to use to resolve the
	 *                     enumeration into a value.
	 */
	public HKXEnumMemberHandler(final ByteBuffer outFile, final long offset, final HKXEnumResolver enumResolver) {
		this.outFile = outFile;
		this.offset = offset;
		this.enumResolver = enumResolver;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXMemberCallback write(final HKXMember member, final long currentPos) {
		HKXEnumMember enumMember = (HKXEnumMember) member;
		if (!enumMember.getEnumerationName().isEmpty()) {
			((Buffer) outFile).position((int) (currentPos + offset));
			long enumVal = enumResolver.resolve(enumMember.getEnumerationName(), enumMember.get());
			byte[] res = ByteUtils.fromULong(enumVal, (int) MemberSizeResolver.getSize(enumMember.getSubtype()));
			outFile.put(res);
		}
		return (memberCallbacks, position) -> {
			return 0;
		};
	}

}
