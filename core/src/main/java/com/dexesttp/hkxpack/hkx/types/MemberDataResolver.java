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
			case TYPE_HALF:
			case TYPE_INT16:
			case TYPE_INT32:
			case TYPE_INT64:
				HKXDirectMember<Integer> member4 = new HKXDirectMember<>(name, type);
				member4.set((int) ByteUtils.getSInt(byteArray));
				return member4;
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
			case TYPE_QSTRANSFORM:
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
		// Default
			default:
				break;
		} throw new IllegalArgumentException(type + " can't be analyzed with MemberTypeResolver#getMember");
	}
}
