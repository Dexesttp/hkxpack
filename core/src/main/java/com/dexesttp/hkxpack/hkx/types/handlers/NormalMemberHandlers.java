package com.dexesttp.hkxpack.hkx.types.handlers;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.resources.byteutils.ByteUtils;

/**
 * Handles normal (32) members
 */
final class NormalMemberHandlers {
	private NormalMemberHandlers() {
		// NO OP
	}

	static MemberHandler createMemberHandler(final HKXType type) {
		switch (type) {
		case TYPE_UINT32:
			return new UInt32MemberHandler();
		case TYPE_INT32:
			return new SInt32MemberHandler();
		default:
			return null;
		}
	}

	/**
	 * Handles UInt32
	 */
	static class UInt32MemberHandler implements MemberHandler {
		@Override
		/**
		 * {@inheritDoc}
		 */
		public long getSize() {
			return 0x04;
		}

		@Override
		/**
		 * {@inheritDoc}
		 */
		public HKXMember createMember(final String name, final HKXType type, final byte[] byteArray) {
			HKXDirectMember<Integer> member3 = new HKXDirectMember<>(name, type);
			member3.set((int) ByteUtils.getUInt(byteArray));
			return member3;
		}

		@SuppressWarnings("unchecked")
		@Override
		/**
		 * {@inheritDoc}
		 */
		public byte[] readMember(final HKXMember member) {
			HKXDirectMember<Integer> memberUInt32 = (HKXDirectMember<Integer>) member;
			return ByteUtils.fromULong(memberUInt32.get(), 4);
		}
	}

	/**
	 * Handles Int32
	 */
	static class SInt32MemberHandler implements MemberHandler {
		@Override
		/**
		 * {@inheritDoc}
		 */
		public long getSize() {
			return 0x04;
		}

		@Override
		/**
		 * {@inheritDoc}
		 */
		public HKXMember createMember(final String name, final HKXType type, final byte[] byteArray) {
			HKXDirectMember<Integer> member4 = new HKXDirectMember<>(name, type);
			member4.set((int) ByteUtils.getSInt(byteArray));
			return member4;
		}

		@SuppressWarnings("unchecked")
		@Override
		/**
		 * {@inheritDoc}
		 */
		public byte[] readMember(final HKXMember member) {
			HKXDirectMember<Integer> memberInt32 = (HKXDirectMember<Integer>) member;
			return ByteUtils.fromSLong(memberInt32.get(), 4);
		}
	}
}
