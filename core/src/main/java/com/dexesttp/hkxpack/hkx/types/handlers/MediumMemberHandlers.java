package com.dexesttp.hkxpack.hkx.types.handlers;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.resources.byteutils.ByteUtils;

/**
 * Handles medium (16-32) members
 */
class MediumMemberHandlers {
	private MediumMemberHandlers() {
		// NO OP
	}

	static MemberHandler createMemberHandler(final HKXType type) {
		switch(type) {
			case TYPE_UINT16:
				return new UInt16MemberHandler();
			case TYPE_INT16:
				return new SInt16MemberHandler();
			default:
				return null;
		}
	}
	
	/**
	 * Handles UInt16
	 */
	static class UInt16MemberHandler implements MemberHandler {
		@Override
		/**
		 * {@inheritDoc}
		 */
		public long getSize() {
			return 0x02;
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
			HKXDirectMember<Integer> memberUInt16 = (HKXDirectMember<Integer>) member;
			return ByteUtils.fromULong(memberUInt16.get(), 2);
		}
	}
	
	/**
	 * Handles Int16
	 */
	static class SInt16MemberHandler implements MemberHandler {
		@Override
		/**
		 * {@inheritDoc}
		 */
		public long getSize() {
			return 0x02;
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
			HKXDirectMember<Integer> memberInt16 = (HKXDirectMember<Integer>) member;
			return ByteUtils.fromSLong(memberInt16.get(), 2);
		}
	}
}
