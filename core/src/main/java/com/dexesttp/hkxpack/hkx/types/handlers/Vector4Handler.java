package com.dexesttp.hkxpack.hkx.types.handlers;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.resources.byteutils.ByteUtils;

/**
 * Handles quaternions 
 */
class Vector4Handler implements MemberHandler {
	@Override
	/**
	 * {@inheritDoc}
	 */
	public long getSize() {
		return 0x10;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXMember createMember(final String name, final HKXType type, final byte[] byteArray) {
		byte[] b21 = new byte[] {byteArray[0], byteArray[1], byteArray[2], byteArray[3]};
		byte[] b22 = new byte[] {byteArray[4], byteArray[5], byteArray[6], byteArray[7]};
		byte[] b23 = new byte[] {byteArray[8], byteArray[9], byteArray[10], byteArray[11]};
		byte[] b24 = new byte[] {byteArray[12], byteArray[13], byteArray[14], byteArray[15]};
		HKXDirectMember<Double[]> member8 = new HKXDirectMember<>(name, type);
		member8.set(new Double[] {
				(double) ByteUtils.getFloat(b21),
				(double) ByteUtils.getFloat(b22),
				(double) ByteUtils.getFloat(b23),
				(double) ByteUtils.getFloat(b24)
		});
		return member8;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * {@inheritDoc}
	 */
	public byte[] readMember(final HKXMember member) {
		HKXDirectMember<Double[]> memberTransform = (HKXDirectMember<Double[]>) member;
		byte[] memberTr1 = ByteUtils.fromFloat(memberTransform.get()[0], 4);
		byte[] memberTr2 = ByteUtils.fromFloat(memberTransform.get()[1], 4);
		byte[] memberTr3 = ByteUtils.fromFloat(memberTransform.get()[2], 4);
		byte[] memberTr4 = ByteUtils.fromFloat(memberTransform.get()[3], 4);
		return new byte[]{
				memberTr1[0], memberTr1[1], memberTr1[2], memberTr1[3],
				memberTr2[0], memberTr2[1], memberTr2[2], memberTr2[3],
				memberTr3[0], memberTr3[1], memberTr3[2], memberTr3[3],
				memberTr4[0], memberTr4[1], memberTr4[2], memberTr4[3]
		};
	}
}
