package com.dexesttp.hkxpack.descriptor.members;

import com.dexesttp.hkxpack.descriptor.enums.Flag;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;

/**
 * Template of an {@link HKXObject}.
 */
public class HKXMemberTemplate {
	
	/**
	 * Name of the member
	 */
	public final transient String name;

	/**
	 * Offset of the member in the class
	 */
	public final transient long offset;

	/**
	 * Type of the member <br />
	 * The type is expected to be set for each member.
	 * If the type is UNKNOWN, the program is expected to throw an exception or warn the user.
	 */
	public final transient HKXType vtype;
	
	/**
	 * Eventual subtype of the member. <br />
	 * Should be TYPE_NONE if the memebr doesn't expect a subtype.
	 */
	public final transient HKXType vsubtype;
	
	/**
	 * Eventual target type of the member. <br />
	 * Should be empty if the member doesn't expect a target type.
	 */
	public final transient String target;
	
	/**
	 * Eventual array size of the member. <br />
	 * If the member have an array size, it is expected to consider the vtype as the array's content type.
	 */
	public final transient int arrsize;
	
	/**
	 * Flags of the member. <br />
	 * The flag is expected to be set for each member.
	 * If the flag is UNKNOWN, the program is expected to throw an exception or warn the user.
	 */
	public final transient Flag flag;
	
	/**
	 * Create a member template.
	 * <p>
	 * If you're creating a member template, you're either doing something very wrong or very technical.<br />
	 * Use {@link com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory#get(String name)}
	 * to create a {@link com.dexesttp.hkxpack.descriptor.HKXDescriptor}
	 * which contains HKXMemberTemplates
	 * @param name
	 * @param vtype
	 * @param vsubtype
	 * @param ctype
	 * @param etype
	 * @param arrsize
	 * @param flag
	 */
	public HKXMemberTemplate(
			final String name, final String offset, final String vtype,
			final String vsubtype, final String ctype, final String etype,
			final String arrsize, final String flag) {
		this.name = name;
		this.offset = Long.parseLong(offset);
		this.vtype = HKXType.valueOf(vtype);
		this.vsubtype = HKXType.valueOf(vsubtype);
		if(!ctype.isEmpty()) {
			this.target = ctype;
		} else if(!etype.isEmpty()) {
			this.target = etype;
		} else {
			this.target = "";
		}
		this.arrsize = Integer.parseInt(arrsize);
		this.flag = Flag.valueOf(flag);
	}
}
