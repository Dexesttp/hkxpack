package com.dexesttp.hkxpack.data.members;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.HKXObject;

/**
 * A {@link HKXData} type used as children of a {@link HKXObject}. 
 */
public interface HKXMember extends HKXData {
	/**
	 * Get the name of this member.
	 * @return the member's name, as a {@link String}.
	 */
	String getName();
}
