package com.dexesttp.hkxpack.hkx.structs;

import java.util.List;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.l10n.SBundle;
import com.dexesttp.hkxpack.resources.ByteUtils;

// TODO move that to a better place.
/**
 * Intended to retrieve {@link HKXType}-specific data.
 * <p>
 * {@link #getSize(HKXType)} retrieves the size of a {@link HKXType}
 * {@link #getSize(HKXDescriptor)} retrieves the size of a {@link HKXDescriptor}, including padding.
 * {@link #getMember(String, HKXType, byte[])} converts a {@link byte} array to a {@link HKXMember},
 * 		given the {@link HKXType} of the member is standard. 
 */
public class MemberTypeResolver {
	private static final long PTR_SIZE = 8;

	/**
	 * Retrieve the size of a standard {@link HKXType}.
	 * @param type the {@link HKXType} to retrieve the size of.
	 * @return the {@link HKXType}'s size.
	 * @throws IllegalArgumentException if the given {@link HKXType} isn't standard.
	 */
	public static long getSize(HKXType type) {
		switch(type) {
			case TYPE_NONE:
			case TYPE_VOID:
				return 0X00;
			case TYPE_ENUM:
			case TYPE_FLAGS:
				return 0X04;
		// Base values
			case TYPE_BOOL:
				return 0X01;
			case TYPE_CHAR:
				return 0X01;
			case TYPE_UINT8:
			case TYPE_INT8:
				return 0X01;
			case TYPE_HALF:
			case TYPE_UINT16:
			case TYPE_INT16:
				return 0X02;
			case TYPE_ULONG:
			case TYPE_UINT32:
			case TYPE_INT32:
				return 0X04;
			case TYPE_UINT64:
			case TYPE_INT64:
				return 0X08;
			case TYPE_REAL:
				return 0X08;
		// Complex values
			case TYPE_MATRIX3:
				return 0x0A;
			case TYPE_QUATERNION:
			case TYPE_QSTRANSFORM:
			case TYPE_VECTOR4:
				return 0x10;
		// Strings and ptrs
			case TYPE_CSTRING:
			case TYPE_STRINGPTR:
				return PTR_SIZE;
			case TYPE_FUNCTIONPOINTER:
			case TYPE_POINTER:
				return PTR_SIZE;
		// Arrays
			case TYPE_RELARRAY:
				return 0x4;
			case TYPE_ARRAY:
			case TYPE_SIMPLEARRAY:
				return 0X10;
			default:
				break;
		}
		throw new IllegalArgumentException(type.toString() + " can't be analyzed with MemberTypeResolver#getSize");
	}

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
	
	/**
	 * Retrieves the size of a {@link HKXDescriptor}, including end padding if needed.
	 * @param descriptor the {@link HKXDescriptor} to retrieve the size from.
	 * @return the {@link HKXDescriptor}'s size.
	 */
	public static long getSize(HKXDescriptor descriptor) {
		List<HKXMemberTemplate> templates = descriptor.getMemberTemplates();
		if(templates.isEmpty())
			return 0;
		HKXMemberTemplate lastTemplate = templates.get(templates.size() - 1);
		if(lastTemplate.vtype != HKXType.TYPE_STRUCT)
			return snapSize(lastTemplate.offset + getSize(lastTemplate.vtype));
		else
			throw new RuntimeException(SBundle.getString("bug.known") + " [#343]");
	}

	// TODO maybe improve SnapSize if it happens it isn't good enough.
	/**
	 * Snap to the next 0x04 factor if needed.
	 * @param l the value to snap.
	 * @return the snapped size.
	 */
	private static long snapSize(long l) {
		long smallSize = l / 4;
		smallSize *= 4;
		if(l == smallSize)
			return l;
		else
			return smallSize + 4;
	}
}
