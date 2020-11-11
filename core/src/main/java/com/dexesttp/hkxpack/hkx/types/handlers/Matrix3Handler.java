package com.dexesttp.hkxpack.hkx.types.handlers;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.resources.byteutils.ByteUtils;

/**
 * Handles Matrix3 or QTransform members
 */
class Matrix3Handler implements MemberHandler {
	@Override
	/**
	 * {@inheritDoc}
	 */
	public long getSize() {
		return 0x30;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXMember createMember(final String name, final HKXType type, final byte[] byteArray) {
		byte[] b31 = new byte[] { byteArray[0], byteArray[1], byteArray[2], byteArray[3] };
		byte[] b32 = new byte[] { byteArray[4], byteArray[5], byteArray[6], byteArray[7] };
		byte[] b33 = new byte[] { byteArray[8], byteArray[9], byteArray[10], byteArray[11] };
		byte[] b34 = new byte[] { byteArray[12], byteArray[13], byteArray[14], byteArray[15] };
		byte[] b35 = new byte[] { byteArray[16], byteArray[17], byteArray[18], byteArray[19] };
		byte[] b36 = new byte[] { byteArray[20], byteArray[21], byteArray[22], byteArray[23] };
		byte[] b37 = new byte[] { byteArray[24], byteArray[25], byteArray[26], byteArray[27] };
		byte[] b38 = new byte[] { byteArray[28], byteArray[29], byteArray[30], byteArray[31] };
		byte[] b39 = new byte[] { byteArray[32], byteArray[33], byteArray[34], byteArray[35] };
		byte[] b3a = new byte[] { byteArray[36], byteArray[37], byteArray[38], byteArray[39] };
		byte[] b3b = new byte[] { byteArray[40], byteArray[41], byteArray[42], byteArray[43] };
		byte[] b3c = new byte[] { byteArray[44], byteArray[45], byteArray[46], byteArray[47] };
		HKXDirectMember<Double[]> member9 = new HKXDirectMember<>(name, type);
		member9.set(new Double[] { (double) ByteUtils.getFloat(b31), (double) ByteUtils.getFloat(b32),
				(double) ByteUtils.getFloat(b33), (double) ByteUtils.getFloat(b34), (double) ByteUtils.getFloat(b35),
				(double) ByteUtils.getFloat(b36), (double) ByteUtils.getFloat(b37), (double) ByteUtils.getFloat(b38),
				(double) ByteUtils.getFloat(b39), (double) ByteUtils.getFloat(b3a), (double) ByteUtils.getFloat(b3b),
				(double) ByteUtils.getFloat(b3c) });
		return member9;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * {@inheritDoc}
	 */
	public byte[] readMember(final HKXMember member) {
		HKXDirectMember<Double[]> memberQs = (HKXDirectMember<Double[]>) member;
		byte[][] memberQs1 = new byte[][] { ByteUtils.fromFloat(memberQs.get()[0], 4),
				ByteUtils.fromFloat(memberQs.get()[1], 4), ByteUtils.fromFloat(memberQs.get()[2], 4),
				ByteUtils.fromFloat(memberQs.get()[3], 4), };
		byte[][] memberQs2 = new byte[][] { ByteUtils.fromFloat(memberQs.get()[4], 4),
				ByteUtils.fromFloat(memberQs.get()[5], 4), ByteUtils.fromFloat(memberQs.get()[6], 4),
				ByteUtils.fromFloat(memberQs.get()[7], 4), };
		byte[][] memberQs3 = new byte[][] { ByteUtils.fromFloat(memberQs.get()[8], 4),
				ByteUtils.fromFloat(memberQs.get()[9], 4), ByteUtils.fromFloat(memberQs.get()[10], 4),
				ByteUtils.fromFloat(memberQs.get()[11], 4), };
		return new byte[] { memberQs1[0][0], memberQs1[0][1], memberQs1[0][2], memberQs1[0][3], memberQs1[1][0],
				memberQs1[1][1], memberQs1[1][2], memberQs1[1][3], memberQs1[2][0], memberQs1[2][1], memberQs1[2][2],
				memberQs1[2][3], memberQs1[3][0], memberQs1[3][1], memberQs1[3][2], memberQs1[3][3],

				memberQs2[0][0], memberQs2[0][1], memberQs2[0][2], memberQs2[0][3], memberQs2[1][0], memberQs2[1][1],
				memberQs2[1][2], memberQs2[1][3], memberQs2[2][0], memberQs2[2][1], memberQs2[2][2], memberQs2[2][3],
				memberQs2[3][0], memberQs2[3][1], memberQs2[3][2], memberQs2[3][3],

				memberQs3[0][0], memberQs3[0][1], memberQs3[0][2], memberQs3[0][3], memberQs3[1][0], memberQs3[1][1],
				memberQs3[1][2], memberQs3[1][3], memberQs3[2][0], memberQs3[2][1], memberQs3[2][2], memberQs3[2][3],
				memberQs3[3][0], memberQs3[3][1], memberQs3[3][2], memberQs3[3][3] };
	}
}
