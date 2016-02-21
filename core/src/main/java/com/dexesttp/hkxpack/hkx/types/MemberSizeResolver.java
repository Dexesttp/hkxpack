package com.dexesttp.hkxpack.hkx.types;

import java.util.List;

import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.l10n.SBundle;

/**
 * Intended to retrieve {@link HKXType}-specific data.
 * <p>
 * {@link #getSize(HKXType)} retrieves the size of a {@link HKXType}
 * {@link #getSize(HKXDescriptor)} retrieves the size of a {@link HKXDescriptor}, including padding.
 */
public class MemberSizeResolver {
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
			case TYPE_VECTOR4:
			case TYPE_QUATERNION:
			case TYPE_TRANSFORM:
				return 0x10;
			case TYPE_QSTRANSFORM:
				return 0x30;
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
