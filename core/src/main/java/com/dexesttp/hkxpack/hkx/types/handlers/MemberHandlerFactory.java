package com.dexesttp.hkxpack.hkx.types.handlers;

import com.dexesttp.hkxpack.descriptor.enums.HKXType;

/**
 * Creates a MemberHandler
 */
public final class MemberHandlerFactory {
	private MemberHandlerFactory() {
		// NO OP
	}

	/**
	 * Get a {@link MemberHandler} from a {@link HKXType}.
	 * 
	 * @param type the {@link HKXType}.
	 * @return the {@link MemberHandler}.
	 */
	public static MemberHandler getMemberHandler(final HKXType type) {
		switch (type) {
		// Base values
		case TYPE_BOOL:
		case TYPE_CHAR:
		case TYPE_UINT8:
		case TYPE_INT8:
			return SmallMemberHandlers.createMemberHandler(type);
		case TYPE_UINT16:
		case TYPE_INT16:
			return MediumMemberHandlers.createMemberHandler(type);
		case TYPE_INT32:
		case TYPE_UINT32:
			return NormalMemberHandlers.createMemberHandler(type);
		case TYPE_ULONG:
		case TYPE_UINT64:
		case TYPE_INT64:
			return BigMemberHandlers.createMemberHandler(type);
		case TYPE_HALF:
		case TYPE_REAL:
			return RealMemberHandlers.createMemberHandler(type);
		// Complex values
		case TYPE_VECTOR4:
		case TYPE_QUATERNION:
			return new Vector4Handler();
		case TYPE_MATRIX3:
		case TYPE_QSTRANSFORM:
			return new Matrix3Handler();
		case TYPE_MATRIX4:
		case TYPE_TRANSFORM:
			return new Matrix4Handler();
		// Default
		default:
			break;
		}
		throw new IllegalArgumentException(type + " can't be analyzed with MemberTypeResolver#getMember");
	}
}
