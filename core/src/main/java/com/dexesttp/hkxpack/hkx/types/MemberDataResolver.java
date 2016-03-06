package com.dexesttp.hkxpack.hkx.types;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.resources.ByteUtils;

/**
 * Intended to retrieve {@link HKXType}-specific data.
 * {@link #getMember(String, HKXType, byte[])} converts a {@link byte} array to a {@link HKXMember},
 * 		given the {@link HKXType} of the member is standard.
 */
public class MemberDataResolver {
	/**
	 * Read a simple / defined member from a byte array.
	 * @param name the name of the member to create.
	 * @param type the {@link HKXType} of data to convert the array into.
	 * @param byteArray the {@link byte} array to read the member from.
	 * @return the {@link HKXMember} containing the data.
	 * @throws IllegalArgumentException if the given {@link HKXType} isn't standard.
	 */
	public static HKXMember getMember(String name, HKXType type, byte[] byteArray) {
		switch(type) {
		// Base values
			case TYPE_BOOL:
				HKXDirectMember<Boolean> member1 = new HKXDirectMember<>(name, type);
				member1.set(ByteUtils.getInt(byteArray) > 0);
				return member1;
			case TYPE_CHAR:
			case TYPE_UINT8:
			case TYPE_INT8:
				HKXDirectMember<Character> member2 = new HKXDirectMember<>(name, type);
				member2.set((char) ByteUtils.getInt(byteArray));
				return member2;
			case TYPE_UINT16:
			case TYPE_ULONG:
			case TYPE_UINT32:
			case TYPE_UINT64:
				HKXDirectMember<Integer> member3 = new HKXDirectMember<>(name, type);
				member3.set((int) ByteUtils.getInt(byteArray));
				return member3;
			case TYPE_INT16:
			case TYPE_INT32:
			case TYPE_INT64:
				HKXDirectMember<Integer> member4 = new HKXDirectMember<>(name, type);
				member4.set((int) ByteUtils.getSInt(byteArray));
				return member4;
			case TYPE_HALF:
			case TYPE_REAL:
				HKXDirectMember<Double> member5 = new HKXDirectMember<>(name, type);
				member5.set((double) ByteUtils.getFloat(byteArray));
				return member5;
		// Complex values
			case TYPE_MATRIX3:
				byte[] b11 = new byte[] {byteArray[0], byteArray[1], byteArray[2], byteArray[3]};
				byte[] b12 = new byte[] {byteArray[4], byteArray[5], byteArray[6], byteArray[7]};
				byte[] b13 = new byte[] {byteArray[8], byteArray[9], byteArray[10], byteArray[11]};
				HKXDirectMember<Double[]> member7 = new HKXDirectMember<>(name, type);
				member7.set(new Double[] {
						(double) ByteUtils.getFloat(b11),
						(double) ByteUtils.getFloat(b12),
						(double) ByteUtils.getFloat(b13)
				});
				return member7;
			case TYPE_VECTOR4:
			case TYPE_QUATERNION:
			case TYPE_TRANSFORM:
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
			case TYPE_QSTRANSFORM:
				byte[] b31 = new byte[] {byteArray[0], byteArray[1], byteArray[2], byteArray[3]};
				byte[] b32 = new byte[] {byteArray[4], byteArray[5], byteArray[6], byteArray[7]};
				byte[] b33 = new byte[] {byteArray[8], byteArray[9], byteArray[10], byteArray[11]};
				byte[] b34 = new byte[] {byteArray[12], byteArray[13], byteArray[14], byteArray[15]};
				byte[] b35 = new byte[] {byteArray[16], byteArray[17], byteArray[18], byteArray[19]};
				byte[] b36 = new byte[] {byteArray[20], byteArray[21], byteArray[22], byteArray[23]};
				byte[] b37 = new byte[] {byteArray[24], byteArray[25], byteArray[26], byteArray[27]};
				byte[] b38 = new byte[] {byteArray[28], byteArray[29], byteArray[30], byteArray[31]};
				byte[] b39 = new byte[] {byteArray[32], byteArray[33], byteArray[34], byteArray[35]};
				byte[] b3a = new byte[] {byteArray[36], byteArray[37], byteArray[38], byteArray[39]};
				byte[] b3b = new byte[] {byteArray[40], byteArray[41], byteArray[42], byteArray[43]};
				byte[] b3c = new byte[] {byteArray[44], byteArray[45], byteArray[46], byteArray[47]};
				HKXDirectMember<Double[]> member9 = new HKXDirectMember<>(name, type);
				member9.set(new Double[] {
						(double) ByteUtils.getFloat(b31),
						(double) ByteUtils.getFloat(b32),
						(double) ByteUtils.getFloat(b33),
						(double) ByteUtils.getFloat(b34),
						(double) ByteUtils.getFloat(b35),
						(double) ByteUtils.getFloat(b36),
						(double) ByteUtils.getFloat(b37),
						(double) ByteUtils.getFloat(b38),
						(double) ByteUtils.getFloat(b39),
						(double) ByteUtils.getFloat(b3a),
						(double) ByteUtils.getFloat(b3b),
						(double) ByteUtils.getFloat(b3c)
				});
				return member9;
			case TYPE_MATRIX4:
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
		// Default
			default:
				break;
		} throw new IllegalArgumentException(type + " can't be analyzed with MemberTypeResolver#getMember");
	}
	
	/**
	 * Write a simple / defined member to a byte array.
	 * @param member the {@link HKXMember} of data to create the array from.
	 * @return the byte aray containing the data.
	 * @throws IllegalArgumentException if the given {@link HKXMember} isn't standard.
	 */
	@SuppressWarnings("unchecked")
	public static byte[] fromMember(HKXMember member) {
		switch(member.getType()) {
		// Base values
			case TYPE_BOOL:
				HKXDirectMember<Boolean> member1 = (HKXDirectMember<Boolean>) member;
				return new byte[]{(byte) (member1.get() ? 0x01 : 0x00)};
			case TYPE_CHAR:
			case TYPE_UINT8:
				HKXDirectMember<Character> memberUInt8 = (HKXDirectMember<Character>) member;
				return ByteUtils.fromLong(memberUInt8.get(), 1);
			case TYPE_INT8:
				HKXDirectMember<Character> memberInt8 = (HKXDirectMember<Character>) member;
				return ByteUtils.fromSLong(memberInt8.get(), 1);
			case TYPE_UINT16:
				HKXDirectMember<Integer> memberUInt16 = (HKXDirectMember<Integer>) member;
				return ByteUtils.fromLong(memberUInt16.get(), 2);
			case TYPE_INT16:
				HKXDirectMember<Integer> memberInt16 = (HKXDirectMember<Integer>) member;
				return ByteUtils.fromSLong(memberInt16.get(), 2);
			case TYPE_UINT32:
				HKXDirectMember<Integer> memberUInt32 = (HKXDirectMember<Integer>) member;
				return ByteUtils.fromLong(memberUInt32.get(), 4);
			case TYPE_INT32:
				HKXDirectMember<Integer> memberInt32 = (HKXDirectMember<Integer>) member;
				return ByteUtils.fromSLong(memberInt32.get(), 4);
			case TYPE_ULONG:
			case TYPE_UINT64:
				HKXDirectMember<Integer> memberUInt64 = (HKXDirectMember<Integer>) member;
				return ByteUtils.fromLong(memberUInt64.get(), 8);
			case TYPE_INT64:
				HKXDirectMember<Integer> memberInt64 = (HKXDirectMember<Integer>) member;
				return ByteUtils.fromLong(memberInt64.get(), 8);
			case TYPE_HALF:
				HKXDirectMember<Double> memberHalf = (HKXDirectMember<Double>) member;
				return ByteUtils.fromFloat(memberHalf.get(), 2);
			case TYPE_REAL:
				HKXDirectMember<Double> memberReal = (HKXDirectMember<Double>) member;
				return ByteUtils.fromFloat(memberReal.get(), 4);
		// Complex values
			case TYPE_MATRIX3:
				HKXDirectMember<Double[]> memberMt3 = (HKXDirectMember<Double[]>) member;
				byte[] memberMt3_1 = ByteUtils.fromFloat(memberMt3.get()[0], 4);
				byte[] memberMt3_2 = ByteUtils.fromFloat(memberMt3.get()[1], 4);
				byte[] memberMt3_3 = ByteUtils.fromFloat(memberMt3.get()[2], 4);
				return new byte[]{
						memberMt3_1[0], memberMt3_1[1], memberMt3_1[2], memberMt3_1[3],
						memberMt3_2[0], memberMt3_2[1], memberMt3_2[2], memberMt3_2[3],
						memberMt3_3[0], memberMt3_3[1], memberMt3_3[2], memberMt3_3[3]
				};
			case TYPE_VECTOR4:
			case TYPE_QUATERNION:
			case TYPE_TRANSFORM:
				HKXDirectMember<Double[]> memberTransform = (HKXDirectMember<Double[]>) member;
				byte[] memberTr_1 = ByteUtils.fromFloat(memberTransform.get()[0], 4);
				byte[] memberTr_2 = ByteUtils.fromFloat(memberTransform.get()[1], 4);
				byte[] memberTr_3 = ByteUtils.fromFloat(memberTransform.get()[2], 4);
				byte[] memberTr_4 = ByteUtils.fromFloat(memberTransform.get()[3], 4);
				return new byte[]{
						memberTr_1[0], memberTr_1[1], memberTr_1[2], memberTr_1[3],
						memberTr_2[0], memberTr_2[1], memberTr_2[2], memberTr_2[3],
						memberTr_3[0], memberTr_3[1], memberTr_3[2], memberTr_3[3],
						memberTr_4[0], memberTr_4[1], memberTr_4[2], memberTr_4[3]
				};
			case TYPE_QSTRANSFORM:
				HKXDirectMember<Double[]> memberQs = (HKXDirectMember<Double[]>) member;
				byte[][] memberQs_1 = new byte[][] {
					ByteUtils.fromFloat(memberQs.get()[0], 4),
					ByteUtils.fromFloat(memberQs.get()[1], 4),
					ByteUtils.fromFloat(memberQs.get()[2], 4),
					ByteUtils.fromFloat(memberQs.get()[3], 4),
				};
				byte[][] memberQs_2 = new byte[][] {
					ByteUtils.fromFloat(memberQs.get()[4], 4),
					ByteUtils.fromFloat(memberQs.get()[5], 4),
					ByteUtils.fromFloat(memberQs.get()[6], 4),
					ByteUtils.fromFloat(memberQs.get()[7], 4),
				};
				byte[][] memberQs_3 = new byte[][] {
					ByteUtils.fromFloat(memberQs.get()[8], 4),
					ByteUtils.fromFloat(memberQs.get()[9], 4),
					ByteUtils.fromFloat(memberQs.get()[10], 4),
					ByteUtils.fromFloat(memberQs.get()[11], 4),
				};
				return new byte[]{
						memberQs_1[0][0], memberQs_1[0][1], memberQs_1[0][2], memberQs_1[0][3],
						memberQs_1[1][0], memberQs_1[1][1], memberQs_1[1][2], memberQs_1[1][3],
						memberQs_1[2][0], memberQs_1[2][1], memberQs_1[2][2], memberQs_1[2][3],
						memberQs_1[3][0], memberQs_1[3][1], memberQs_1[3][2], memberQs_1[3][3],

						memberQs_2[0][0], memberQs_2[0][1], memberQs_2[0][2], memberQs_2[0][3],
						memberQs_2[1][0], memberQs_2[1][1], memberQs_2[1][2], memberQs_2[1][3],
						memberQs_2[2][0], memberQs_2[2][1], memberQs_2[2][2], memberQs_2[2][3],
						memberQs_2[3][0], memberQs_2[3][1], memberQs_2[3][2], memberQs_2[3][3],

						memberQs_3[0][0], memberQs_3[0][1], memberQs_3[0][2], memberQs_3[0][3],
						memberQs_3[1][0], memberQs_3[1][1], memberQs_3[1][2], memberQs_3[1][3],
						memberQs_3[2][0], memberQs_3[2][1], memberQs_3[2][2], memberQs_3[2][3],
						memberQs_3[3][0], memberQs_3[3][1], memberQs_3[3][2], memberQs_3[3][3]
				};
			case TYPE_MATRIX4:
				HKXDirectMember<Double[]> memberM4 = (HKXDirectMember<Double[]>) member;
				byte[][] memberM4_1 = new byte[][] {
					ByteUtils.fromFloat(memberM4.get()[0], 4),
					ByteUtils.fromFloat(memberM4.get()[1], 4),
					ByteUtils.fromFloat(memberM4.get()[2], 4),
					ByteUtils.fromFloat(memberM4.get()[3], 4)
				};
				byte[][] memberM4_2 = new byte[][] {
					ByteUtils.fromFloat(memberM4.get()[4], 4),
					ByteUtils.fromFloat(memberM4.get()[5], 4),
					ByteUtils.fromFloat(memberM4.get()[6], 4),
					ByteUtils.fromFloat(memberM4.get()[7], 4)
				};
				byte[][] memberM4_3 = new byte[][] {
					ByteUtils.fromFloat(memberM4.get()[8], 4),
					ByteUtils.fromFloat(memberM4.get()[9], 4),
					ByteUtils.fromFloat(memberM4.get()[10], 4),
					ByteUtils.fromFloat(memberM4.get()[11], 4)
				};
				byte[][] memberM4_4 = new byte[][] {
					ByteUtils.fromFloat(memberM4.get()[12], 4),
					ByteUtils.fromFloat(memberM4.get()[13], 4),
					ByteUtils.fromFloat(memberM4.get()[14], 4),
					ByteUtils.fromFloat(memberM4.get()[15], 4)
				};
				return new byte[]{
						memberM4_1[0][0], memberM4_1[0][1], memberM4_1[0][2], memberM4_1[0][3],
						memberM4_1[1][0], memberM4_1[1][1], memberM4_1[1][2], memberM4_1[1][3],
						memberM4_1[2][0], memberM4_1[2][1], memberM4_1[2][2], memberM4_1[2][3],
						memberM4_1[3][0], memberM4_1[3][1], memberM4_1[3][2], memberM4_1[3][3],

						memberM4_2[0][0], memberM4_2[0][1], memberM4_2[0][2], memberM4_2[0][3],
						memberM4_2[1][0], memberM4_2[1][1], memberM4_2[1][2], memberM4_2[1][3],
						memberM4_2[2][0], memberM4_2[2][1], memberM4_2[2][2], memberM4_2[2][3],
						memberM4_2[3][0], memberM4_2[3][1], memberM4_2[3][2], memberM4_2[3][3],

						memberM4_3[0][0], memberM4_3[0][1], memberM4_3[0][2], memberM4_3[0][3],
						memberM4_3[1][0], memberM4_3[1][1], memberM4_3[1][2], memberM4_3[1][3],
						memberM4_3[2][0], memberM4_3[2][1], memberM4_3[2][2], memberM4_3[2][3],
						memberM4_3[3][0], memberM4_3[3][1], memberM4_3[3][2], memberM4_3[3][3],

						memberM4_4[0][0], memberM4_4[0][1], memberM4_4[0][2], memberM4_4[0][3],
						memberM4_4[1][0], memberM4_4[1][1], memberM4_4[1][2], memberM4_4[1][3],
						memberM4_4[2][0], memberM4_4[2][1], memberM4_4[2][2], memberM4_4[2][3],
						memberM4_4[3][0], memberM4_4[3][1], memberM4_4[3][2], memberM4_4[3][3]
				};
		// Default
			default:
				break;
		} throw new IllegalArgumentException(member.getType() + " can't be analyzed with MemberTypeResolver#fromMember");
	}
}
