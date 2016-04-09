package com.dexesttp.hkxpack.hkx.types;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadException;
import com.dexesttp.hkxpack.hkx.types.object.ObjectSize;

/**
 * Resolve the size of a {@link HKXObject} or a {@link HKXDescriptor}.
 */
public final class ObjectSizeResolver {
	
	private ObjectSizeResolver() {
		// NO OP
	}
	
	/**
	 * Retrieves the size of a {@link HKXDescriptor}, including end padding if needed.
	 * @param descriptor the {@link HKXDescriptor} to retrieve the size from.
	 * @return the {@link HKXDescriptor}'s size, in bytes.
	 * @throws ClassFileReadException if there was an error resolving this {@link HKXDescriptor}'s subclass 
	 */
	public static long getSize(final HKXDescriptor descriptor, final HKXDescriptorFactory descriptorFactory) throws ClassFileReadException {
		return ObjectSize.getSize(descriptor, descriptorFactory);
	}

	/**
	 * Retrieves the size of a {@link HKXObject}, including end padding if needed.
	 * @param object the {@link HKXObject} to retrieve the size from.
	 * @return the {@link HKXObject}'s size, in bytes.
	 */
	public static long getSize(final HKXObject object) {
		return ObjectSize.getSize(object);
	}
}
