package com.dexesttp.hkxpack.hkx.structs;

import java.util.List;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.l10n.SBundle;
import com.dexesttp.hkxpack.resources.ByteUtils;

public class MemberTypeResolver {
	private static final long PTR_SIZE = 8;

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

	public static HKXMember getMember(String name, HKXType type, byte[] b) {
		switch(type) {
		// Base values
			case TYPE_BOOL:
				HKXDirectMember<Boolean> member1 = new HKXDirectMember<>(name, type);
				member1.set(ByteUtils.getInt(b) > 0);
				return member1;
			case TYPE_CHAR:
			case TYPE_UINT8:
			case TYPE_INT8:
				HKXDirectMember<Character> member2 = new HKXDirectMember<>(name, type);
				member2.set((char) ByteUtils.getInt(b));
				return member2;
			case TYPE_UINT16:
			case TYPE_ULONG:
			case TYPE_UINT32:
			case TYPE_UINT64:
				HKXDirectMember<Integer> member3 = new HKXDirectMember<>(name, type);
				member3.set((int) ByteUtils.getInt(b));
				return member3;
			case TYPE_HALF:
			case TYPE_INT16:
			case TYPE_INT32:
			case TYPE_INT64:
				HKXDirectMember<Integer> member4 = new HKXDirectMember<>(name, type);
				member4.set((int) ByteUtils.getSInt(b));
				return member4;
			case TYPE_REAL:
				HKXDirectMember<Double> member5 = new HKXDirectMember<>(name, type);
				member5.set((double) ByteUtils.getFloat(b));
				return member5;
			case TYPE_ENUM:
			case TYPE_FLAGS:
				HKXDirectMember<String> member6 = new HKXDirectMember<>(name, type);
				member6.set("TODO : read enums");
				return member6;
		// Complex values
			case TYPE_MATRIX3:
				byte[] b11 = new byte[] {b[0], b[1], b[2], b[3]};
				byte[] b12 = new byte[] {b[4], b[5], b[6], b[7]};
				byte[] b13 = new byte[] {b[8], b[9], b[10], b[11]};
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
				byte[] b21 = new byte[] {b[0], b[1], b[2], b[3]};
				byte[] b22 = new byte[] {b[4], b[5], b[6], b[7]};
				byte[] b23 = new byte[] {b[8], b[9], b[10], b[11]};
				byte[] b24 = new byte[] {b[12], b[13], b[14], b[15]};
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

	/**
	 * Snap to the next 0x04 factor if needed.
	 * TODO maybe improve this if it happens it is not good enough
	 * @param l
	 * @return
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
