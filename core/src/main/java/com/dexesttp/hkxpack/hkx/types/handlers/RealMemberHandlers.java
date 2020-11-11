package com.dexesttp.hkxpack.hkx.types.handlers;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.resources.byteutils.ByteUtils;

/**
 * Create real (or half) member handlers.
 */
final class RealMemberHandlers {
	private RealMemberHandlers() {
		// NO OP
	}

	static MemberHandler createMemberHandler(final HKXType type) {
		switch (type) {
		case TYPE_HALF:
			return new HalfMemberHandler();
		case TYPE_REAL:
			return new RealMemberHandler();
		default:
			return null;
		}
	}

	/**
	 * Half handler
	 */
	static class HalfMemberHandler implements MemberHandler {
		@Override
		/**
		 * {@inheritDoc}
		 */
		public long getSize() {
			return 0x02;
		}

		@Override
		@SuppressWarnings("unchecked")
		/**
		 * {@inheritDoc}
		 */
		public byte[] readMember(final HKXMember member) {
			HKXDirectMember<Double> memberHalf = (HKXDirectMember<Double>) member;
			return ByteUtils.fromFloat(memberHalf.get(), 2);
		}

		@Override
		/**
		 * {@inheritDoc}
		 */
		public HKXMember createMember(final String name, final HKXType type, final byte[] byteArray) {
			return createReal(name, type, byteArray);
		}
	};

	/**
	 * Real handler
	 */
	static class RealMemberHandler implements MemberHandler {
		@Override
		/**
		 * {@inheritDoc}
		 */
		public long getSize() {
			return 0x04;
		}

		@SuppressWarnings("unchecked")
		@Override
		/**
		 * {@inheritDoc}
		 */
		public byte[] readMember(final HKXMember member) {
			HKXDirectMember<Double> memberReal = (HKXDirectMember<Double>) member;
			return ByteUtils.fromFloat(memberReal.get(), 4);
		}

		@Override
		/**
		 * {@inheritDoc}
		 */
		public HKXMember createMember(final String name, final HKXType type, final byte[] byteArray) {
			return createReal(name, type, byteArray);
		}
	};

	private static HKXMember createReal(final String name, final HKXType type, final byte[] byteArray) {
		HKXDirectMember<Double> member5 = new HKXDirectMember<>(name, type);
		member5.set((double) ByteUtils.getFloat(byteArray));
		return member5;
	}
}
