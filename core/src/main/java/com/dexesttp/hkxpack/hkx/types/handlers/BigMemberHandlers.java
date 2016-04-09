package com.dexesttp.hkxpack.hkx.types.handlers;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.resources.byteutils.ByteUtils;

/**
 * Handles big (64) members
 */
final class BigMemberHandlers {
	private BigMemberHandlers() {
		// NO OP
	}

	static MemberHandler createMemberHandler(final HKXType type) {
		switch(type) {
			case TYPE_UINT64:
			case TYPE_ULONG:
				return new UInt64Handler();
			case TYPE_INT64:
				return new SInt64Handler();
			default:
				return null;
		}
	}

	/**
	 * Handles UInt64/ULong
	 */
	static class UInt64Handler implements MemberHandler {
		@Override
		/**
		 * {@inheritDoc}
		 */
		public long getSize() {
			return 0x08;
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
			HKXDirectMember<Integer> memberUInt64 = (HKXDirectMember<Integer>) member;
			return ByteUtils.fromULong(memberUInt64.get(), 8);
		}
	}

	/**
	 * Handles Int64
	 */
	static class SInt64Handler implements MemberHandler {
		@Override
		/**
		 * {@inheritDoc}
		 */
		public long getSize() {
			return 0x08;
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
			HKXDirectMember<Integer> memberInt64 = (HKXDirectMember<Integer>) member;
			return ByteUtils.fromULong(memberInt64.get(), 8);
		}
	}
}
