package com.dexesttp.hkxpack.hkx.types.handlers;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.resources.byteutils.ByteUtils;

/**
 * Create small (<= 8) member handlers
 */
final class SmallMemberHandlers {
	private SmallMemberHandlers() {
		// NO OP
	}

	static MemberHandler createMemberHandler(final HKXType type) {
		switch (type) {
		case TYPE_BOOL:
			return new BoolMemberHandler();
		case TYPE_CHAR:
		case TYPE_UINT8:
			return new CharMemberHandler();
		case TYPE_INT8:
			return new SCharMemberHandler();
		default:
			return null;
		}
	}

	/**
	 * Bool handler
	 */
	static class BoolMemberHandler implements MemberHandler {
		@Override
		/**
		 * {@inheritDoc}
		 */
		public long getSize() {
			return 0x01;
		}

		@Override
		/**
		 * {@inheritDoc}
		 */
		public HKXMember createMember(final String name, final HKXType type, final byte[] byteArray) {
			HKXDirectMember<Boolean> member1 = new HKXDirectMember<>(name, HKXType.TYPE_BOOL);
			member1.set(ByteUtils.getUInt(byteArray) > 0);
			return member1;
		}

		@SuppressWarnings("unchecked")
		@Override
		/**
		 * {@inheritDoc}
		 */
		public byte[] readMember(final HKXMember member) {
			HKXDirectMember<Boolean> memberBool = (HKXDirectMember<Boolean>) member;
			return new byte[] { (byte) (memberBool.get() ? 0x01 : 0x00) };
		}
	}

	/**
	 * Char/UInt8 handler
	 */
	static class CharMemberHandler implements MemberHandler {
		@Override
		/**
		 * {@inheritDoc}
		 */
		public long getSize() {
			return 0x01;
		}

		@Override
		/**
		 * {@inheritDoc}
		 */
		public HKXMember createMember(final String name, final HKXType type, final byte[] byteArray) {
			return createChar(name, type, byteArray);
		}

		@SuppressWarnings("unchecked")
		@Override
		/**
		 * {@inheritDoc}
		 */
		public byte[] readMember(final HKXMember member) {
			HKXDirectMember<Character> memberUInt8 = (HKXDirectMember<Character>) member;
			return ByteUtils.fromULong(memberUInt8.get(), 1);
		}
	}

	/**
	 * Int8 handler
	 */
	static class SCharMemberHandler implements MemberHandler {
		@Override
		/**
		 * {@inheritDoc}
		 */
		public long getSize() {
			return 0x01;
		}

		@Override
		/**
		 * {@inheritDoc}
		 */
		public HKXMember createMember(final String name, final HKXType type, final byte[] byteArray) {
			return createChar(name, type, byteArray);
		}

		@SuppressWarnings("unchecked")
		@Override
		/**
		 * {@inheritDoc}
		 */
		public byte[] readMember(final HKXMember member) {
			HKXDirectMember<Character> memberInt8 = (HKXDirectMember<Character>) member;
			return ByteUtils.fromSLong(memberInt8.get(), 1);
		}
	}

	private static HKXMember createChar(final String name, final HKXType type, final byte[] byteArray) {
		HKXDirectMember<Character> member2 = new HKXDirectMember<>(name, type);
		member2.set((char) ByteUtils.getUInt(byteArray));
		return member2;
	}
}
