package com.dexesttp.hkxpack.data;

import com.dexesttp.hkxpack.descriptor.enums.HKXType;

/**
 * General purpose data storage for a HKX file DOM equivalent.
 */
public interface HKXData {
	/**
	 * Get this object's type.
	 * @return this object's {@link HKXType}.
	 */
	HKXType getType();
}
