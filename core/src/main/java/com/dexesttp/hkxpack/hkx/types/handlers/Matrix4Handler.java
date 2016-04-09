package com.dexesttp.hkxpack.hkx.types.handlers;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.resources.byteutils.ByteUtils;

/**
 * Handles a Matrix4 or Transform element.
 */
class Matrix4Handler implements MemberHandler {
	@Override
	/**
	 * {@inheritDoc}
	 */
	public long getSize() {
		return 0x40;
	}
	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXMember createMember(final String name, final HKXType type, final byte[] byteArray) {
		byte[] b41 = new byte[] {byteArray[0], byteArray[1], byteArray[2], byteArray[3]};
		byte[] b42 = new byte[] {byteArray[4], byteArray[5], byteArray[6], byteArray[7]};
		byte[] b43 = new byte[] {byteArray[8], byteArray[9], byteArray[10], byteArray[11]};
		byte[] b44 = new byte[] {byteArray[12], byteArray[13], byteArray[14], byteArray[15]};
		byte[] b45 = new byte[] {byteArray[16], byteArray[17], byteArray[18], byteArray[19]};
		byte[] b46 = new byte[] {byteArray[20], byteArray[21], byteArray[22], byteArray[23]};
		byte[] b47 = new byte[] {byteArray[24], byteArray[25], byteArray[26], byteArray[27]};
		byte[] b48 = new byte[] {byteArray[28], byteArray[29], byteArray[30], byteArray[31]};
		byte[] b49 = new byte[] {byteArray[32], byteArray[33], byteArray[34], byteArray[35]};
		byte[] b4a = new byte[] {byteArray[36], byteArray[37], byteArray[38], byteArray[39]};
		byte[] b4b = new byte[] {byteArray[40], byteArray[41], byteArray[42], byteArray[43]};
		byte[] b4c = new byte[] {byteArray[44], byteArray[45], byteArray[46], byteArray[47]};
		byte[] b4d = new byte[] {byteArray[48], byteArray[49], byteArray[50], byteArray[51]};
		byte[] b4e = new byte[] {byteArray[52], byteArray[53], byteArray[54], byteArray[55]};
		byte[] b4f = new byte[] {byteArray[56], byteArray[57], byteArray[58], byteArray[59]};
		byte[] b4g = new byte[] {byteArray[60], byteArray[61], byteArray[62], byteArray[63]};
		HKXDirectMember<Double[]> member10 = new HKXDirectMember<>(name, type);
		member10.set(new Double[] {
				(double) ByteUtils.getFloat(b41),
				(double) ByteUtils.getFloat(b42),
				(double) ByteUtils.getFloat(b43),
				(double) ByteUtils.getFloat(b44),
				(double) ByteUtils.getFloat(b45),
				(double) ByteUtils.getFloat(b46),
				(double) ByteUtils.getFloat(b47),
				(double) ByteUtils.getFloat(b48),
				(double) ByteUtils.getFloat(b49),
				(double) ByteUtils.getFloat(b4a),
				(double) ByteUtils.getFloat(b4b),
				(double) ByteUtils.getFloat(b4c),
				(double) ByteUtils.getFloat(b4d),
				(double) ByteUtils.getFloat(b4e),
				(double) ByteUtils.getFloat(b4f),
				(double) ByteUtils.getFloat(b4g)
		});
		return member10;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * {@inheritDoc}
	 */
	public byte[] readMember(final HKXMember member) {
		HKXDirectMember<Double[]> memberM4 = (HKXDirectMember<Double[]>) member;
		byte[][] memberM41 = new byte[][] {
			ByteUtils.fromFloat(memberM4.get()[0], 4),
			ByteUtils.fromFloat(memberM4.get()[1], 4),
			ByteUtils.fromFloat(memberM4.get()[2], 4),
			ByteUtils.fromFloat(memberM4.get()[3], 4)
		};
		byte[][] memberM42 = new byte[][] {
			ByteUtils.fromFloat(memberM4.get()[4], 4),
			ByteUtils.fromFloat(memberM4.get()[5], 4),
			ByteUtils.fromFloat(memberM4.get()[6], 4),
			ByteUtils.fromFloat(memberM4.get()[7], 4)
		};
		byte[][] memberM43 = new byte[][] {
			ByteUtils.fromFloat(memberM4.get()[8], 4),
			ByteUtils.fromFloat(memberM4.get()[9], 4),
			ByteUtils.fromFloat(memberM4.get()[10], 4),
			ByteUtils.fromFloat(memberM4.get()[11], 4)
		};
		byte[][] memberM44 = new byte[][] {
			ByteUtils.fromFloat(memberM4.get()[12], 4),
			ByteUtils.fromFloat(memberM4.get()[13], 4),
			ByteUtils.fromFloat(memberM4.get()[14], 4),
			ByteUtils.fromFloat(memberM4.get()[15], 4)
		};
		return new byte[]{
				memberM41[0][0], memberM41[0][1], memberM41[0][2], memberM41[0][3],
				memberM41[1][0], memberM41[1][1], memberM41[1][2], memberM41[1][3],
				memberM41[2][0], memberM41[2][1], memberM41[2][2], memberM41[2][3],
				memberM41[3][0], memberM41[3][1], memberM41[3][2], memberM41[3][3],

				memberM42[0][0], memberM42[0][1], memberM42[0][2], memberM42[0][3],
				memberM42[1][0], memberM42[1][1], memberM42[1][2], memberM42[1][3],
				memberM42[2][0], memberM42[2][1], memberM42[2][2], memberM42[2][3],
				memberM42[3][0], memberM42[3][1], memberM42[3][2], memberM42[3][3],

				memberM43[0][0], memberM43[0][1], memberM43[0][2], memberM43[0][3],
				memberM43[1][0], memberM43[1][1], memberM43[1][2], memberM43[1][3],
				memberM43[2][0], memberM43[2][1], memberM43[2][2], memberM43[2][3],
				memberM43[3][0], memberM43[3][1], memberM43[3][2], memberM43[3][3],

				memberM44[0][0], memberM44[0][1], memberM44[0][2], memberM44[0][3],
				memberM44[1][0], memberM44[1][1], memberM44[1][2], memberM44[1][3],
				memberM44[2][0], memberM44[2][1], memberM44[2][2], memberM44[2][3],
				memberM44[3][0], memberM44[3][1], memberM44[3][2], memberM44[3][3]
		};
	}

}
